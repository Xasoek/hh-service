package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.dto.CreateUserRequest;
import com.github.xasoek.hh_service.dto.UserResponse;
import com.github.xasoek.hh_service.entity.User;
import com.github.xasoek.hh_service.mapper.UserMapper;
import com.github.xasoek.hh_service.repository.UserRepository;
import com.github.xasoek.hh_service.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository  userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        User user = UserMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
