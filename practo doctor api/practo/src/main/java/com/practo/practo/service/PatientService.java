package com.practo.practo.service;

import com.practo.practo.entity.Patient;
import com.practo.practo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient createPatient(Patient patient){
        Patient savedPatient = patientRepository.save(patient);
        return patient;
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Get a specific patient by ID
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Patient not found"));
    }

    // Delete a patient
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }



}

