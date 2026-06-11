package com.github.xasoek.hh_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.xasoek.hh_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
