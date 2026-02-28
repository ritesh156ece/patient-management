package com.pm.patientservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PatientServiceApplication {

  public static void main(String[] args) {
    log.info("Starting Patient Service Application...");
    SpringApplication.run(PatientServiceApplication.class, args);
  }
}
