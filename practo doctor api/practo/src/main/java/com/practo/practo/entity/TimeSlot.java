package com.practo.practo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "available_slots", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "time", "doctor_id"})
})
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId; // New field for doctor-specific slots
}
