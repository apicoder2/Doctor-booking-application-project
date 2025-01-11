package com.practo.practo.controller;

import com.practo.practo.entity.Doctor;
import com.practo.practo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/addDoctor")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.addDoctor(doctor);
        return ResponseEntity.ok(savedDoctor);
    }


    /**
     * Search doctors by multiple criteria.
     *
     * @param name           doctor's name (optional)
     * @param specialization doctor's specialization (optional)
     * @param hospital       doctor's hospital (optional)
     * @param experience     minimum experience (optional)
     * @return List of doctors matching the criteria
     */
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String hospital,
            @RequestParam(required = false) Integer experience) {

        List<Doctor> doctors = doctorService.searchDoctors(name, specialization, hospital, experience);
        return ResponseEntity.ok(doctors);
    }

    // Get all doctors
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    // Get a specific doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    // Delete a doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>("Doctor is deleted !", HttpStatus.OK);
    }

}

