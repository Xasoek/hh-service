package com.github.xasoek.hh_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class CreateUserRequest {

    @NotBlank(message = "Name cannot be empty")
    public String name;

    @Email(message = "Invalid email")
    public String email;
}