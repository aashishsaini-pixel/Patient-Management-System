package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {


    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        log.info("Get all patients, Found {} patients", patients.size());
        return patients.stream().map(patientMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists with email " + patientRequestDTO.getEmail());
        }
        Patient patient = patientMapper.toEntity(patientRequestDTO);
        return  patientMapper.toDto(patientRepository.save(patient));
    }

    @Transactional
    public PatientResponseDTO updatePatient(UUID id , PatientRequestDTO patientRequestDTO) {
        log.info("Update patient with id {}", id);
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient is not present with id : " + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail() , id)) {
            log.error("Email already exists with email {}", patientRequestDTO.getEmail());
            throw new EmailAlreadyExistsException("Email already exists with email " + patientRequestDTO.getEmail());
        }

        patientMapper.updatePatient(patientRequestDTO , patient);
        Patient updatedPatient = patientRepository.save(patient);
        log.info("Patient is saved in the db with udpated name {}", updatedPatient.getName());
        return patientMapper.toDto(patientRepository.save(patient));
    }

}
