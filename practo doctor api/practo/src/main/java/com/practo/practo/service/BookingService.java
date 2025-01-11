package com.practo.practo.service;

import com.practo.practo.config.TimeSlotManager;
import com.practo.practo.entity.Booking;
import com.practo.practo.payload.BookingDto;
import com.practo.practo.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private TimeSlotManager timeSlotManager;

    private ExecutorService executor = Executors.newCachedThreadPool(); // Dynamic thread pool

    public synchronized String bookAnAppointment(BookingDto bookingDto) {
        if (!bookingDto.isValidBookingTime()) {
            return "Invalid booking time. Available times: 10:15 AM, 11:15 AM, 12:15 PM.";
        }

        List<String> availableSlots = timeSlotManager.getAvailableTimeSlot(bookingDto.getBookingDate(), bookingDto.getDoctorId());
        if (!availableSlots.contains(bookingDto.getBookingTime())) {
            return "Selected time slot is not available for the doctor.";
        }

        timeSlotManager.removeTimeSlot(bookingDto.getBookingDate(), bookingDto.getBookingTime(), bookingDto.getDoctorId());

        Booking booking = new Booking();
        booking.setDoctorId(bookingDto.getDoctorId());
        booking.setPatientId(bookingDto.getPatientId());
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setBookingTime(bookingDto.getBookingTime());

        bookingRepo.save(booking);
        return "Booking confirmed for doctor ID: " + bookingDto.getDoctorId() +
                ", date: " + bookingDto.getBookingDate() +
                ", and time slot: " + bookingDto.getBookingTime();
    }

    public String cancelBooking(Long bookingId) {
        return bookingRepo.findById(bookingId).map(booking -> {
            timeSlotManager.addTimeSlot(booking.getBookingDate(), booking.getBookingTime(), booking.getDoctorId());
            bookingRepo.delete(booking);
            return "Booking canceled and time slot freed.";
        }).orElse("Booking not found.");
    }

    @PreDestroy
    public void shutdownScheduler() {
        logger.info("Attempting to shut down executor service...");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                logger.warn("Executor did not terminate in the specified time. Forcing shutdown...");
                executor.shutdownNow();
            } else {
                logger.info("Executor service shut down successfully.");
            }
        } catch (InterruptedException e) {
            logger.error("Shutdown interrupted. Forcing shutdown now.", e);
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
