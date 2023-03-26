package com.example.uberDeluxe.service;

import com.example.uberDeluxe.cloud.CloudService;
import com.example.uberDeluxe.data.dto.request.EmailNotificationRequest;
import com.example.uberDeluxe.data.dto.request.Recipient;
import com.example.uberDeluxe.data.dto.request.RegisterDriverRequest;
import com.example.uberDeluxe.data.dto.response.RegisterResponse;
import com.example.uberDeluxe.data.models.AppUser;
import com.example.uberDeluxe.data.models.Driver;
import com.example.uberDeluxe.data.models.Role;
import com.example.uberDeluxe.data.repositories.DriverRepository;
import com.example.uberDeluxe.exception.ImageUploadException;
import com.example.uberDeluxe.notification.MailService;
import com.example.uberDeluxe.utils.AppUtilities;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService{
    private final DriverRepository driverRepository;
    private final CloudService cloudService;
    private final MailService mailService;
    private ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterDriverRequest request) {
        AppUser driverDetails = modelMapper.map(request, AppUser.class);
        driverDetails.setPassword(passwordEncoder.encode(request.getPassword()));
        driverDetails.setCreatedAt(LocalDateTime.now().toString());
        driverDetails.setRoles(new HashSet<>());
        driverDetails.getRoles().add(Role.DRIVER);

        // upload drivers license image
        var imageUrl = cloudService.upload(request.getLicenseImage());
        if(imageUrl == null) throw new ImageUploadException("Driver Registration failed");

        //create driver object
        Driver driver = Driver.builder()
                .userDetails(driverDetails)
                .licenseImage(imageUrl)
                .build();

        Driver savedDriver = driverRepository.save(driver);

        EmailNotificationRequest emailRequest = buildNotificationRequest(savedDriver.getUserDetails().getEmail(), savedDriver.getUserDetails().getName(), driver.getId());
        String response = mailService.sendHtmlMail(emailRequest);
        if(response == null) return  getRegisterFailureResponse();
        return RegisterResponse.builder()
                .id(savedDriver.getId())
                .isSuccess(true)
                .message("Driver Registration Successful")
                .build();
    }

    private RegisterResponse getRegisterFailureResponse() {
        return RegisterResponse.builder()
                .id(-1L)
                .isSuccess(false)
                .message("Driver Registration Failed")
                .build();
    }

    private EmailNotificationRequest buildNotificationRequest(String email, String name, Long id) {
        EmailNotificationRequest request = new EmailNotificationRequest();
        request.getTo().add(new Recipient(name, email));
        String template = AppUtilities.getMailTemplate();
        String content = String.format(template, name, AppUtilities.generateVerificationLink(id));
        request.setHtmlContent(content);
        return request;
    }

    @Override
    public Optional<Driver> getDriverBy(Long driverId) {
        return driverRepository.findById(driverId);
    }

    @Override
    public void saveDriver(Driver driver) {
        driverRepository.save(driver);
    }
}
