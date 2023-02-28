package com.example.uberDeluxe.data.repositories;

import com.example.uberDeluxe.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
