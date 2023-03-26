package com.example.uberDeluxe.notification;

import com.example.uberDeluxe.data.dto.request.EmailNotificationRequest;
import com.example.uberDeluxe.data.dto.request.Recipient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MailjetMailServiceImplTest {
    @Autowired
    private MailService mailService;
    private EmailNotificationRequest emailNotificationRequest;


    @BeforeEach
    void setUp() {
        List<Recipient> to = List.of(
                new Recipient("dean", "0.michael@gmail.com")
        );
    }
}