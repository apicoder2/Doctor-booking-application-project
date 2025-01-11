package com.practo.practo.controller;

import com.practo.practo.entity.Review;
import com.practo.practo.exception.ResourceNotFoundException;
import com.practo.practo.payload.DoctorDto;
import com.practo.practo.payload.ReviewDto;
import com.practo.practo.repository.DoctorRepository;
import com.practo.practo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private DoctorRepository doctorRepo;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewDto reviewDto) {
        Review savedReview = reviewService.createReview(reviewDto);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<DoctorDto> getReviewsByDoctorId(@PathVariable Long doctorId) {
        DoctorDto doctorDto = reviewService.getReviewsByDoctorIdWithDetails(doctorId);
        return ResponseEntity.ok(doctorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>("Review is deleted !", HttpStatus.OK);
    }
}
