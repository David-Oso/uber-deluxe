package com.example.uberDeluxe.cloud;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class CloudinaryCloudServiceImplTest {
    @Autowired
    private CloudService cloudService;
    private MockMultipartFile file;


    @BeforeEach
    void setUp() throws IOException {
        file = new MockMultipartFile("semicolonInSpace",
                new FileInputStream("C:\\Users\\User\\IdeaProjects\\uberDeluxe\\uberDeluxe\\src\\test\\resources\\IMG-20220727-WA0004.jpg"));
    }
    @Test
    void uploadTest(){
        var cloudinaryImageUrl = cloudService.upload(file);
        log.info("img_url::{}", cloudinaryImageUrl);
        assertThat(cloudinaryImageUrl).isNotNull();
    }
}