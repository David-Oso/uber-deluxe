package com.example.uberDeluxe.notification;

import com.example.uberDeluxe.data.dto.request.EmailNotificationRequest;

public interface MailService {
    String sendHtmlMail(EmailNotificationRequest request);
}
