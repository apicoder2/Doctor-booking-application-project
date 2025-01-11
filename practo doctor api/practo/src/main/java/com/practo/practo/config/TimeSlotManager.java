package com.practo.practo.config;

import com.practo.practo.entity.TimeSlot;
import com.practo.practo.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeSlotManager {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    public synchronized List<String> getAvailableTimeSlot(LocalDate date, Long doctorId) {
        return timeSlotRepository.findByDateAndDoctorIdAndIsAvailableTrue(date, doctorId).stream()
                .map(TimeSlot::getTime)
                .collect(Collectors.toList());
    }

    public synchronized void removeTimeSlot(LocalDate date, String timeSlot, Long doctorId) {
        TimeSlot slot = timeSlotRepository.findByDateAndTimeAndDoctorId(date, timeSlot, doctorId);
        if (slot != null && slot.getIsAvailable()) {
            slot.setIsAvailable(false);
            timeSlotRepository.save(slot);
        }
    }

    public synchronized void addTimeSlot(LocalDate date, String timeSlot, Long doctorId) {
        if (!timeSlotRepository.existsByDateAndTimeAndDoctorId(date, timeSlot, doctorId)) {
            TimeSlot newSlot = new TimeSlot();
            newSlot.setDate(date);
            newSlot.setTime(timeSlot);
            newSlot.setIsAvailable(true);
            newSlot.setDoctorId(doctorId);
            timeSlotRepository.save(newSlot);
        } else {
            TimeSlot existingSlot = timeSlotRepository.findByDateAndTimeAndDoctorId(date, timeSlot, doctorId);
            if (existingSlot != null && !existingSlot.getIsAvailable()) {
                existingSlot.setIsAvailable(true);
                timeSlotRepository.save(existingSlot);
            }
        }
    }
}

