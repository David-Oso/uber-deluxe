package com.example.uberDeluxe.services;

import com.example.uberDeluxe.data.dto.request.RegisterPassengerRequest;
import com.example.uberDeluxe.data.dto.response.RegisterResponse;
import com.example.uberDeluxe.data.models.Passenger;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.data.domain.Page;

public interface PassengerService {
    RegisterResponse register(RegisterPassengerRequest registerRequest);
    Passenger getPassengerById(Long userId);
    Passenger updatePassenger(Long passengerId, JsonPatch updatePayload);
    Page<Passenger> getAllPassenger(int pageNumber);
    void deletePassenger(Long id);
}
