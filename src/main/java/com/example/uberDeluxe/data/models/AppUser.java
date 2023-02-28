package com.example.uberDeluxe.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUser {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
    private String email;
    @Transient
    private MultipartFile profileImage;
    private String createdAt;
}
