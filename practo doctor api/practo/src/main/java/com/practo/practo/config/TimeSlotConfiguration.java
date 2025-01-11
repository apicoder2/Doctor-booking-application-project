package com.practo.practo.config;

import com.practo.practo.entity.TimeSlot;
import com.practo.practo.repository.TimeSlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class TimeSlotConfiguration {

    @Bean
    public CommandLineRunner initializeTimeSlots(TimeSlotRepository timeSlotRepository) {
        return args -> {
            LocalDate today = LocalDate.now();
            for (Long doctorId = 1L; doctorId <= 3L; doctorId++) { // Example for 3 doctors
                timeSlotRepository.saveAll(Arrays.asList(
                        new TimeSlot(null, today, "10:15 AM".toUpperCase(), true, doctorId),
                        new TimeSlot(null, today, "11:15 AM".toUpperCase(), true, doctorId),
                        new TimeSlot(null, today, "12:15 PM".toUpperCase(), true, doctorId)
                ));
            }
        };
    }
}

