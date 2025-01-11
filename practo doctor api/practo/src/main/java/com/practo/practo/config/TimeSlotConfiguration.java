package com.practo.practo.config;

import com.practo.practo.entity.TimeSlot;
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
    public CommandLineRunner initializeTimeSlots(TimeSlotRepository timeSlotRepository) {
        return args -> {
            LocalDate today = LocalDate.now();

            // Fetch all existing time slots for today's date
            List<TimeSlot> existingSlots = timeSlotRepository.findByDate(today);

            for (Long doctorId = 1L; doctorId <= 3L; doctorId++) { // Example for 3 doctors
                List<TimeSlot> newSlots = Arrays.asList(
                        new TimeSlot(null, today, "10:15 AM".toUpperCase(), true, doctorId),
                        new TimeSlot(null, today, "11:15 AM".toUpperCase(), true, doctorId),
                        new TimeSlot(null, today, "12:15 PM".toUpperCase(), true, doctorId)
                );

                for (TimeSlot newSlot : newSlots) {
                    // Check if this slot already exists in the fetched list
                    boolean alreadyExists = existingSlots.stream().anyMatch(existingSlot ->
                            existingSlot.getDate().equals(newSlot.getDate()) &&
                                    existingSlot.getTime().equals(newSlot.getTime()) &&
                                    existingSlot.getDoctorId().equals(newSlot.getDoctorId())
                    );

                    if (!alreadyExists) {
                        // Add the slot if it doesn't exist
                        timeSlotRepository.save(newSlot);
                    } else {
                        System.out.println("Duplicate time slot skipped: " + newSlot);
                    }
                }
            }
        };
    }
}
