package com.github.xasoek.hh_service.repository;

import com.github.xasoek.hh_service.entity.ApplicationStatus;
import com.github.xasoek.hh_service.entity.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
     boolean existsByUserIdAndJobId(Long userId, Long jobId);
     Page<JobApplication> findByUserId(Long userId, Pageable pageable);
     Page<JobApplication> findByJobId(Long jobId, Pageable pageable);
     Page<JobApplication> findByStatus(ApplicationStatus status, Pageable pageable);
}
