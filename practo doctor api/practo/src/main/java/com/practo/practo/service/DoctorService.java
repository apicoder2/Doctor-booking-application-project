package com.practo.practo.service;

import com.practo.practo.entity.Doctor;
import com.practo.practo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    //add Doctor
    public Doctor addDoctor(Doctor doctor) {

        return doctorRepository.save(doctor);
    }

    // Search doctors based on criteria
    public List<Doctor> searchDoctors
    (String name, String specialization, String hospital, Integer experience) {
        return doctorRepository.searchDoctors(name, specialization, hospital, experience);
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Get a specific doctor by ID
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Doctor not found"));
    }

    // Delete a doctor
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
