package com.practo.practo.service;

import com.practo.practo.entity.Doctor;
import com.practo.practo.entity.Patient;
import com.practo.practo.entity.Review;
import com.practo.practo.exception.ResourceNotFoundException;
import com.practo.practo.payload.DoctorDto;
import com.practo.practo.payload.ReviewDto;
import com.practo.practo.repository.DoctorRepository;
import com.practo.practo.repository.PatientRepository;
import com.practo.practo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    public Review createReview(ReviewDto reviewDto) {
        Doctor doctor = doctorRepo.findById(reviewDto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + reviewDto.getDoctorId()));

        Patient patient = patientRepo.findById(reviewDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + reviewDto.getPatientId()));

        Review review = new Review();
        review.setDoctor(doctor);
        review.setPatient(patient);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        return reviewRepo.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));
    }

    public DoctorDto getReviewsByDoctorIdWithDetails(Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + doctorId));

        List<Review> reviews = reviewRepo.findByDoctorId(doctorId);

        double totalRating = reviews.stream().mapToDouble(Review::getRating).sum();
        double averageRating = reviews.isEmpty() ? 0 : totalRating / reviews.size();

        double ratingPercentage = (averageRating / 5.0) * 100.0;

        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setDoctor(doctor);
        doctorDto.setReviews(reviews);
        doctorDto.setRatingPercentage(ratingPercentage);

        return doctorDto;
    }

    public void deleteReview(Long id) {
        if (!reviewRepo.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with ID: " + id);
        }
        reviewRepo.deleteById(id);
    }
}
