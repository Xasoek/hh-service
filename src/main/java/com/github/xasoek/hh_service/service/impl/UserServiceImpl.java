package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.dto.CreateUserRequest;
import com.github.xasoek.hh_service.dto.UserResponse;
import com.github.xasoek.hh_service.entity.Role;
import com.github.xasoek.hh_service.entity.User;
import com.github.xasoek.hh_service.mapper.UserMapper;
import com.github.xasoek.hh_service.repository.UserRepository;
import com.github.xasoek.hh_service.security.SecurityUtil;
import com.github.xasoek.hh_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        ensureHr();

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAll() {
        ensureHr();

        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    private void ensureHr() {
        User currentUser = userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        if (currentUser.getRole() != Role.HR) {
            throw new AccessDeniedException("Only HR can manage users");
        }
    }
}
