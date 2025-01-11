package com.practo.practo.config;

import com.practo.practo.entity.Doctor;
import com.practo.practo.entity.TimeSlot;
import com.practo.practo.repository.DoctorRepository;
import com.practo.practo.repository.TimeSlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
public class TimeSlotConfiguration {

    @Bean
    public CommandLineRunner initializeTimeSlots(TimeSlotRepository timeSlotRepository, DoctorRepository doctorRepository) {
        return args -> {
            LocalDate today = LocalDate.now();

            List<Doctor> doctors = doctorRepository.findAll();
            if (doctors.isEmpty()) {
                System.out.println("No doctors found. Skipping time slot initialization.");
                return;
            }

            for (Doctor doctor : doctors) {
                Long doctorId = doctor.getId();

                List<TimeSlot> newSlots = Arrays.asList(
                        new TimeSlot(null, today, "10:15 AM", true, doctorId),
                        new TimeSlot(null, today, "11:15 AM", true, doctorId),
                        new TimeSlot(null, today, "12:15 PM", true, doctorId)
                );

                List<TimeSlot> existingSlots = timeSlotRepository.findByDateAndDoctorId(today, doctorId);

                List<TimeSlot> uniqueSlots = newSlots.stream()
                        .filter(newSlot -> existingSlots.stream().noneMatch(existingSlot ->
                                existingSlot.getDate().equals(newSlot.getDate()) &&
                                        existingSlot.getTime().equals(newSlot.getTime()) &&
                                        existingSlot.getDoctorId().equals(newSlot.getDoctorId())))
                        .toList();

                if (!uniqueSlots.isEmpty()) {
                    timeSlotRepository.saveAll(uniqueSlots);
                    System.out.println("Added time slots for doctor ID " + doctorId);
                } else {
                    System.out.println("No new time slots needed for doctor ID " + doctorId);
                }
            }
        };
    }
}
