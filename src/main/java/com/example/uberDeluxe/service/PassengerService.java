package com.example.uberDeluxe.service;

import com.example.uberDeluxe.data.dto.request.BookRideRequest;
import com.example.uberDeluxe.data.dto.request.RegisterPassengerRequest;
import com.example.uberDeluxe.data.dto.response.ApiResponse;
import com.example.uberDeluxe.data.dto.response.RegisterResponse;
import com.example.uberDeluxe.data.models.Passenger;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PassengerService {
    RegisterResponse register(RegisterPassengerRequest registerRequest);
    Passenger getPassengerById(Long passengerId);
    void savedPassenger(Passenger passenger);
    Optional<Passenger> getPassengerBy(Long userid);
    Passenger updatePassenger(Long passengerId, JsonPatch updatePayload);
    Page<Passenger> getAllPassenger(int pageNumber);
    void deletePassenger(Long id);
    ApiResponse bookRide(BookRideRequest bookRideRequest);
}
