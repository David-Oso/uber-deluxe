package com.example.uberDeluxe.cloud;

import com.example.uberDeluxe.exception.ImageUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String upload(MultipartFile image) throws ImageUploadException;
}
