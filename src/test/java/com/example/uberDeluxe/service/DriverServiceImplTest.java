package com.example.uberDeluxe.service;

import com.example.uberDeluxe.data.dto.request.RegisterDriverRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;


import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DriverServiceImplTest {
    @Autowired
    private DriverService driverService;
    private RegisterDriverRequest request;

    @BeforeEach
    void setUp() {
        request = new RegisterDriverRequest();
        request.setPassword("test_password");
        request.setName("test driver");
        request.setEmail("test@gmail.com");
    }

    @Test
    void register() throws IOException{
        MockMultipartFile file = new MockMultipartFile("test_license",
                new FileInputStream("C:\\Users\\User\\IdeaProjects\\uberDeluxe\\uberDeluxe\\src\\test\\resources\\IMG-20220727-WA0004.jpg"));
        request.setLicenseImage(file);
        var response = driverService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
    }
}