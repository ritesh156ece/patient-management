package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.ApiException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

  private final PatientRepository patientRepository;
  private final BillingServiceGrpcClient billingServiceGrpcClient;

  public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient) {
    this.patientRepository = patientRepository;
    this.billingServiceGrpcClient = billingServiceGrpcClient;
  }

  public List<PatientResponseDTO> getAllPatients() {
    List<Patient> patients = patientRepository.findAll();
    return patients.stream().map(PatientMapper::toDTO).toList();
  }

  public PatientResponseDTO getPatientById(String id) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid patient id format: " + id);
    }

    Patient patient =
        patientRepository
            .findById(uuid)
            .orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "Patient not found with id: " + id));

    return PatientMapper.toDTO(patient);
  }

  public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
    if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST, "Email already exists: " + patientRequestDTO.getEmail());
    }
    Patient savedPatient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));
    billingServiceGrpcClient.createBillingAccount(
            savedPatient.getId().toString(), savedPatient.getName(), savedPatient.getEmail());
    return PatientMapper.toDTO(savedPatient);
  }

  public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
    Patient existingPatient =
        patientRepository
            .findById(id)
            .orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "Patient not found with id: " + id));

    if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST, "Email already exists: " + patientRequestDTO.getEmail());
    }

    existingPatient.setName(patientRequestDTO.getName());
    existingPatient.setEmail(patientRequestDTO.getEmail());
    existingPatient.setAddress(patientRequestDTO.getAddress());
    existingPatient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

    Patient updatedPatient = patientRepository.save(existingPatient);
    return PatientMapper.toDTO(updatedPatient);
  }

  public void deletePatient(UUID id) {
    if (!patientRepository.existsById(id)) {
      throw new ApiException(HttpStatus.NOT_FOUND, "Patient not found with id: " + id);
    }
    patientRepository.deleteById(id);
  }
}
