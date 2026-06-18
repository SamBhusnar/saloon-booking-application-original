package com.mycompany.category_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
   
}
