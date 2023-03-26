package com.example.uberDeluxe.config.security.services;

import com.example.uberDeluxe.config.security.users.SecureUser;
import com.example.uberDeluxe.data.models.AppUser;
import com.example.uberDeluxe.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final AppUserService appUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserService.getByEmail(username);
        return new SecureUser(user);
    }
}
