package com.github.xasoek.hh_service.dto;

import com.github.xasoek.hh_service.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class CreateUserRequest {

    @NotBlank(message = "Name cannot be empty")
    public String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    public String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    public String password;

    public Role role = Role.USER;
}
