package com.github.xasoek.hh_service.mapper;

import com.github.xasoek.hh_service.dto.CreateUserRequest;
import com.github.xasoek.hh_service.dto.UserResponse;
import com.github.xasoek.hh_service.entity.User;

public class UserMapper {
    public static User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return user;
    }
    public static UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }
}
