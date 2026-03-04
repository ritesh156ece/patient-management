package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;
import java.time.LocalDate;

public class PatientMapper {

  public static PatientResponseDTO toDTO(Patient patient) {
    PatientResponseDTO dto = new PatientResponseDTO();
    dto.setId(patient.getId().toString());
    dto.setName(patient.getName());
    dto.setEmail(patient.getEmail());
    dto.setAddress(patient.getAddress());
    dto.setDateOfBirth(patient.getDateOfBirth().toString());
    return dto;
  }

  public static Patient toEntity(PatientRequestDTO dto) {
    Patient patient = new Patient();
    patient.setName(dto.getName());
    patient.setEmail(dto.getEmail());
    patient.setAddress(dto.getAddress());
    patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
    patient.setRegisteredDate(LocalDate.parse(dto.getRegisteredDate()));
    return patient;
  }
}
