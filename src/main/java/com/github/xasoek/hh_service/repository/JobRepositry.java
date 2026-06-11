package com.github.xasoek.hh_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.xasoek.hh_service.entity.Job;


public interface JobRepositry extends JpaRepository<Job, Long> {


}
