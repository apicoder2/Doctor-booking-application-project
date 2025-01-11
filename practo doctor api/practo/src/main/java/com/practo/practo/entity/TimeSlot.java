package com.practo.practo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "available_slots", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "time", "doctor_id"})
}, indexes = {
        @Index(name = "idx_date_time_doctor", columnList = "date, time, doctor_id")
})
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @Column(name = "time", nullable = false)
    @NotEmpty(message = "Time cannot be empty")
    private String time;

    @Column(name = "is_available", nullable = false)
    @NotNull(message = "Availability status cannot be null")
    private Boolean isAvailable;

    @Column(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", doctorId=" + doctorId +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
