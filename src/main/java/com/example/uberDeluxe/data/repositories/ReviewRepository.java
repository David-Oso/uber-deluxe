package com.example.uberDeluxe.data.repositories;

import com.example.uberDeluxe.data.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
