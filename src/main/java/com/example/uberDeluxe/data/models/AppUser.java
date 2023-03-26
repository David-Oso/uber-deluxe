package com.example.uberDeluxe.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class AppUser {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
//    @Column(unique = true)
    private String email;
    private String profileImage;
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    private String createdAt;
    private Boolean isEnabled;
}
