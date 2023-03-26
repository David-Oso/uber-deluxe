package com.example.uberDeluxe.service;

import com.example.uberDeluxe.data.dto.request.RegisterDriverRequest;
import com.example.uberDeluxe.data.dto.response.RegisterResponse;
import com.example.uberDeluxe.data.models.Driver;

import java.util.Optional;

public interface DriverService {
    RegisterResponse register(RegisterDriverRequest request);
    Optional<Driver> getDriverBy(Long driverId);
    void saveDriver(Driver driver);
}
