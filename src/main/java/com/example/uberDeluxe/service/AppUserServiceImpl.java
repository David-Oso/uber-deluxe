package com.example.uberDeluxe.service;

import com.example.uberDeluxe.cloud.CloudService;
import com.example.uberDeluxe.data.dto.response.ApiResponse;
import com.example.uberDeluxe.data.models.AppUser;
import com.example.uberDeluxe.data.models.Driver;
import com.example.uberDeluxe.data.models.Passenger;
import com.example.uberDeluxe.data.repositories.AppUserRepository;
import com.example.uberDeluxe.exception.BusinessLogicException;
import com.example.uberDeluxe.exception.UserNotFoundException;
import com.example.uberDeluxe.utils.AppUtilities;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements  AppUserService{
    private PassengerService passengerService;
    private DriverService driverService;
    private final CloudService cloudService;
    private final AppUserRepository appUserRepository;

    @Override
    public ApiResponse uploadProfileImage(MultipartFile profileImage, Long userId) {
        Optional<Driver> foundDriver = Optional.empty();
        Optional<Passenger> foundPassenger;
        foundPassenger = findPassenger(userId);
        if(foundPassenger.isEmpty()) foundDriver = findDriver(userId);
        if(foundPassenger.isEmpty() && foundDriver.isEmpty()) throw new UserNotFoundException(
                String.format("user with id %d not found", userId)
        );
        String imageUrl = cloudService.upload(profileImage);
        foundPassenger.ifPresent(passenger -> updatePassengerProfileImage(imageUrl, passenger));
        foundDriver.ifPresent(driver -> updateDriverProfileImage(imageUrl, driver));
        return ApiResponse.builder()
                .message("SUCCESS")
                .build();
    }



    @Override
    public ApiResponse verifyAccount(Long userId, String token) {
        if(AppUtilities.isValidToken(token)) return getVerifiedResponse(userId);
        throw new BusinessLogicException(
                String.format("account verification for user with id %d failed", userId)
        );
    }
    @Override
    public AppUser getByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(
                        "User with email %s not found ... ", email)));
    }

    private ApiResponse getVerifiedResponse(Long userId) {
        Optional<Passenger> foundPassenger;
        Optional<Driver> founDriver = Optional.empty();

        foundPassenger = findPassenger(userId);
        if(foundPassenger.isEmpty()) founDriver = findDriver(userId);
        if(founDriver.isEmpty() && foundPassenger.isEmpty())throw new UserNotFoundException(
                String.format("user with id %d not found", userId)
        );
        founDriver.ifPresent(driver -> enableDriverAccount(driver));
        foundPassenger.ifPresent(passenger -> enablePassengerAccount(passenger));
        return ApiResponse.builder()
                .message("success")
                .build();
    }

    private void enablePassengerAccount(Passenger passenger) {
        passenger.getUserDetails().setIsEnabled(true);
        passengerService.savedPassenger(passenger);
    }

    private void enableDriverAccount(Driver driver) {
        driver.getUserDetails().setIsEnabled(true);
        driverService.saveDriver(driver);
    }

    private void updateDriverProfileImage(String imageUrl, Driver driver) {
        driver.getUserDetails().setProfileImage(imageUrl);
        driverService.saveDriver(driver);
    }

    private void updatePassengerProfileImage(String imageUrl, Passenger passenger) {
        passenger.getUserDetails().setProfileImage(imageUrl);
        passengerService.savedPassenger(passenger);
    }

    private Optional<Passenger> findPassenger(Long userId) {
        return passengerService.getPassengerBy(userId);
    }

    private Optional<Driver> findDriver(Long userId) {
        return driverService.getDriverBy(userId);
    }

}
