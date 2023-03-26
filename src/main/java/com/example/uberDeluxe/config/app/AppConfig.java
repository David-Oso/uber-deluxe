package com.example.uberDeluxe.config.app;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.uberDeluxe.config.mail.MailConfig;
import com.example.uberDeluxe.config.security.utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;
    @Value("${mail.api.key}")
    private String mailApiKey;
    @Value("${sendinblue.mail.url}")
    private String mailUrl;
    @Value("${jwt.secret.key}")
    private String jwtSecret;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret
                )
        );
    }
    @Bean
    public MailConfig mailConfig(){
        return new MailConfig(mailApiKey, mailUrl);
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(jwtSecret);
    }
}
