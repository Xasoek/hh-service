package com.github.xasoek.hh_service.service;

import com.github.xasoek.hh_service.dto.AuthResponse;
import com.github.xasoek.hh_service.dto.LoginRequest;
import com.github.xasoek.hh_service.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

}