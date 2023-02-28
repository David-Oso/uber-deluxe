package com.example.uberDeluxe.services;

import com.example.uberDeluxe.cloud.CloudService;
import com.example.uberDeluxe.data.dto.request.RegisterDriverRequest;
import com.example.uberDeluxe.data.dto.response.RegisterResponse;
import com.example.uberDeluxe.data.models.AppUser;
import com.example.uberDeluxe.data.models.Driver;
import com.example.uberDeluxe.data.repositories.DriverRepository;
import com.example.uberDeluxe.exception.ImageUploadException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService{
    private final DriverRepository driverRepository;
    private final CloudService cloudService;
    private final ModelMapper modelMapper;

    @Override
    public RegisterResponse register(RegisterDriverRequest request, MultipartFile licenseImage) {
        AppUser driverDetails = modelMapper.map(request, AppUser.class);
        driverDetails.setCreatedAt(LocalDateTime.now().toString());

//        steps
//        1. upload drivers license image
        var imageUrl = cloudService.upload(licenseImage);
        if(imageUrl==null)
            throw new ImageUploadException("Driver Registration failed");
//        2. create driver object
        Driver driver = Driver.builder()
                .userDetails(driverDetails)
                .licenseImage(imageUrl)
                .build();
//        3. save driver
        Driver savedDriver = driverRepository.save(driver);

        return RegisterResponse.builder()
                .code(HttpStatus.CREATED.value())
                .id(savedDriver.getId())
                .isSuccess(true)
                .message("Driver Registration Successful")
                .build();
    }
}
