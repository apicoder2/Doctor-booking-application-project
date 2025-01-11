package com.practo.practo.payload;

import com.practo.practo.entity.Doctor;
import com.practo.practo.entity.Review;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {

    @NotNull(message = "Doctor cannot be null")
    @Valid
    private Doctor doctor;

    @NotNull(message = "Reviews cannot be null")
    private List<@Valid Review> reviews;

    @Positive(message = "Rating percentage must be positive")
    private double ratingPercentage;

}