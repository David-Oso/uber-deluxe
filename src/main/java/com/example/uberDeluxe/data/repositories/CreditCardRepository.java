package com.example.uberDeluxe.data.repositories;

import com.example.uberDeluxe.data.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
