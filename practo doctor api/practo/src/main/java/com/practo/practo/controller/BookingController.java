package com.practo.practo.controller;

import com.practo.practo.payload.BookingDto;
import com.practo.practo.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> bookAnAppointment(@RequestBody @Valid BookingDto bookingDto) {
        if (bookingDto.getBookingDate() == null) {
            bookingDto.setBookingDate(LocalDate.now());
        }
        String response = bookingService.bookAnAppointment(bookingDto);
        if (response.startsWith("Booking confirmed")) {
            return ResponseEntity.status(201).body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        String response = bookingService.cancelBooking(id);
        if (response.startsWith("Booking canceled")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
