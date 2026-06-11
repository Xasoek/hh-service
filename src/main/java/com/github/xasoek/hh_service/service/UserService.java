package com.github.xasoek.hh_service.service;

import com.github.xasoek.hh_service.dto.CreateUserRequest;
import com.github.xasoek.hh_service.dto.UserResponse;
import com.github.xasoek.hh_service.entity.User;
import java.util.List;

public interface UserService {
    UserResponse create(CreateUserRequest user);
    List<UserResponse> getAll();
}