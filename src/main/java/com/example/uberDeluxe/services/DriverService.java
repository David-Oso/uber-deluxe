package com.example.uberDeluxe.services;

import com.example.uberDeluxe.data.dto.request.RegisterDriverRequest;
import com.example.uberDeluxe.data.dto.response.RegisterResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DriverService {
    RegisterResponse register(RegisterDriverRequest request, MultipartFile licenseImage);
}
