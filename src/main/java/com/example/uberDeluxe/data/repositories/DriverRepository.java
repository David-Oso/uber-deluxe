package com.example.uberDeluxe.data.repositories;

import com.example.uberDeluxe.data.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
