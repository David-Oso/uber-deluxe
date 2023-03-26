package com.example.uberDeluxe.service;

import com.example.uberDeluxe.data.dto.request.InviteAdminRequest;
import com.example.uberDeluxe.data.dto.response.ApiResponse;

import java.util.Set;

public interface AdminService {
    ApiResponse sendInviteRequests(Set<InviteAdminRequest> inviteAdminRequestList);
}
