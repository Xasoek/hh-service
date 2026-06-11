package com.github.xasoek.hh_service.service;

import com.github.xasoek.hh_service.entity.User;
import java.util.List;

public interface UserService {
    User create(User user);
    List<User> getAll();
}