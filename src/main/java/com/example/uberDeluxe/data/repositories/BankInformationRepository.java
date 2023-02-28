package com.example.uberDeluxe.data.repositories;

import com.example.uberDeluxe.data.models.BankInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankInformationRepository extends JpaRepository<BankInformation, Long> {
}
