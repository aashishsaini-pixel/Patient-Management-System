package com.pm.patient_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PatientNotFoundException extends RuntimeException {
   private final HttpStatus status;
   private final String errorCode;

   public PatientNotFoundException(String message, String errorCode) {
       super(message);
       this.status = HttpStatus.NOT_FOUND;
       this.errorCode = errorCode;
   }

   public PatientNotFoundException(String message) {
       super(message);
       this.status = HttpStatus.NOT_FOUND;
       this.errorCode = "PATIENT_NOT_FOUND";
   }

}
