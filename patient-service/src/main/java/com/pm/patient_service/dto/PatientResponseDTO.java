package com.pm.patient_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientResponseDTO {
  private String id;
  private Integer version;
  private String name;
  private String email;
  private String address;
  private String dateOfBirth;
}