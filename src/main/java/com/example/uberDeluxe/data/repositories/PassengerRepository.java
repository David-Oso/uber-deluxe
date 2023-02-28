package com.example.uberDeluxe.data.repositories;

import com.example.uberDeluxe.data.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
