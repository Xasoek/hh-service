package com.github.xasoek.hh_service.controller;

import com.github.xasoek.hh_service.dto.CreateUserRequest;
import com.github.xasoek.hh_service.dto.UserResponse;
import com.github.xasoek.hh_service.entity.User;
import com.github.xasoek.hh_service.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponse create(@RequestBody CreateUserRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return service.getAll();
    }
}
