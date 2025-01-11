package com.practo.practo.repository;

import com.practo.practo.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByDateAndDoctorIdAndIsAvailableTrue(LocalDate date, Long doctorId);
    TimeSlot findByDateAndTimeAndDoctorId(LocalDate date, String time, Long doctorId);
    boolean existsByDateAndTimeAndDoctorId(LocalDate date, String time, Long doctorId);

    //List<TimeSlot> findByDate(LocalDate today);
    List<TimeSlot> findByDate(LocalDate date);
    List<TimeSlot> findByDateAndDoctorId(LocalDate date, Long doctorId); // Add this method
}

