package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.entity.User;
import com.github.xasoek.hh_service.repository.UserRepository;
import com.github.xasoek.hh_service.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository  userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
