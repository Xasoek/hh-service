package com.github.xasoek.hh_service.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String email;
}
