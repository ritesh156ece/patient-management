package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient API", description = "API for managing patients")
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping
  @Operation(summary = "Get all patients", description = "Retrieve a list of all patients")
  public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
    List<PatientResponseDTO> responseDTOList = patientService.getAllPatients();
    return ResponseEntity.ok().body(responseDTOList);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get patient by ID", description = "Retrieve a patient by their unique ID")
  public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable("id") String id) {
    PatientResponseDTO responseDTO = patientService.getPatientById(id);
    return ResponseEntity.ok().body(responseDTO); // Placeholder
  }

  @PostMapping("/createPatient")
  @Operation(
      summary = "Create a new patient",
      description = "Create a new patient with the provided details")
  public ResponseEntity<PatientResponseDTO> createPatient(
      @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
    PatientResponseDTO responseDTO = patientService.createPatient(patientRequestDTO);
    return ResponseEntity.ok().body(responseDTO); // Placeholder
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Update an existing patient",
      description = "Update the details of an existing patient by their unique ID")
  public ResponseEntity<PatientResponseDTO> updatePatient(
      @PathVariable("id") UUID id,
      @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
    PatientResponseDTO responseDTO = patientService.updatePatient(id, patientRequestDTO);
    return ResponseEntity.ok().body(responseDTO); // Placeholder
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a patient",
      description = "Delete an existing patient by their unique ID")
  public ResponseEntity<Void> deletePatient(@PathVariable("id") UUID id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
