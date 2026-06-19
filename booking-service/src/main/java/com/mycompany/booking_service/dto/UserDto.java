package com.mycompany.booking_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private Long id;
    private String fullName;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;
    private String phone;
    @NotBlank(message = "Role is mandatory")
    private String role;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20, message = "Password must be at least 8 characters   and at most 20 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
            message = "Password must contain uppercase, lowercase, digit and special character"
    )
    private String password;
}
