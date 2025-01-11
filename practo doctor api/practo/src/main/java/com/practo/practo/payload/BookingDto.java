package com.practo.practo.payload;


import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;

    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;

    @NotNull(message = "Booking date cannot be null")
    @FutureOrPresent(message = "Booking date must be today or in the future")
    private LocalDate bookingDate;

    @NotNull(message = "Booking time cannot be null")
    private String bookingTime; // Should be validated with custom logic

    public boolean isValidBookingTime() {
        return bookingTime != null && (
                bookingTime.equalsIgnoreCase("10:15 AM") ||
                        bookingTime.equalsIgnoreCase("11:15 AM") ||
                        bookingTime.equalsIgnoreCase("12:15 PM")
        );
    }

}
