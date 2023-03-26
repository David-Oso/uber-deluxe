package com.example.uberDeluxe.service;

import com.example.uberDeluxe.data.dto.response.ApiResponse;
import com.example.uberDeluxe.data.models.AppUser;
import org.springframework.web.multipart.MultipartFile;

public interface AppUserService {
    ApiResponse uploadProfileImage(MultipartFile profileImage, Long userId);
    ApiResponse verifyAccount(Long userId, String token);
    AppUser getByEmail(String email);
}
