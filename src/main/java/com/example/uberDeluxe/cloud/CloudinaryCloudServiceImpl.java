package com.example.uberDeluxe.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.uberDeluxe.exception.ImageUploadException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CloudinaryCloudServiceImpl implements CloudService{
    private final Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile image){
        try{
            Map<?, ?> response =
                    cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            log.info("response::{}", response);
            return response.get("url").toString();
        }catch (IOException exception){
            throw new ImageUploadException(exception.getMessage());
        }
    }
}
