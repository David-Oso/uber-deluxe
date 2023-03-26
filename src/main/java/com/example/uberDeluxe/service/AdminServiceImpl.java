package com.example.uberDeluxe.service;

import com.example.uberDeluxe.data.dto.request.EmailNotificationRequest;
import com.example.uberDeluxe.data.dto.request.InviteAdminRequest;
import com.example.uberDeluxe.data.dto.request.Recipient;
import com.example.uberDeluxe.data.dto.response.ApiResponse;
import com.example.uberDeluxe.data.models.Admin;
import com.example.uberDeluxe.data.repositories.AdminRepository;
import com.example.uberDeluxe.exception.BusinessLogicException;
import com.example.uberDeluxe.notification.MailService;
import com.example.uberDeluxe.utils.AppUtilities;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;
    private final MailService mailService;
    @Override
    public ApiResponse sendInviteRequests(Set<InviteAdminRequest> inviteAdminRequestList) {
        EmailNotificationRequest request = new EmailNotificationRequest();
        var recipients = inviteAdminRequestList.stream()
                .map(inviteAdminRequest -> createAdminProfile(inviteAdminRequest))
                .map(inviteAdminRequest -> new Recipient(inviteAdminRequest.getUserDetails().getName(), inviteAdminRequest.getUserDetails().getEmail()))
                .toList();
        request.getTo().addAll(recipients);

        String adminMail = AppUtilities.getAdminMailTemplate();
        request.setHtmlContent(String.format(adminMail, "admin", AppUtilities.generateVerificationLink(0L)));
        var response = mailService.sendHtmlMail(request);
        if(response != null) return ApiResponse.builder().message("invite request sent").build();
        throw new BusinessLogicException("invite requests sending failed");
    }

    private Admin createAdminProfile(InviteAdminRequest inviteAdminRequest) {
        Admin admin = new Admin();
        admin.getUserDetails().setName(inviteAdminRequest.getName());
        admin.getUserDetails().setEmail(inviteAdminRequest.getEmail());
        adminRepository.save(admin);
        return admin;
    }
}
