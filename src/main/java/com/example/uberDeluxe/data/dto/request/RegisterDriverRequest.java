package com.example.uberDeluxe.data.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterDriverRequest {
    @NotNull(message= "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    @Min(2)
    @Max(40)
    private String name;
    @NotNull(message= "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "must be a valid email address")
    private String email;
    @NotNull(message= "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    @Size(min = 8, max = 20)
    private String password;
    @NotNull(message = "Please upload license image")
    private MultipartFile licenseImage;
}
