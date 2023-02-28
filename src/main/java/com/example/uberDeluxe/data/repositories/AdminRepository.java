package com.example.uberDeluxe.data.repositories;

import com.example.uberDeluxe.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
