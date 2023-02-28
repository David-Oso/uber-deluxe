package com.example.uberDeluxe.data.repositories;

import com.example.uberDeluxe.data.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
