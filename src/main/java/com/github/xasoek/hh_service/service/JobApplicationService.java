package com.github.xasoek.hh_service.service;


import com.github.xasoek.hh_service.dto.ApplicationResponse;
import com.github.xasoek.hh_service.entity.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobApplicationService {

    List<ApplicationResponse> getAll();

    ApplicationResponse create(Long userId, Long jobId);

    ApplicationResponse updateStatus(Long applicationId, String status);

    Page<ApplicationResponse> getByUserId(Long userId, Pageable pageable);

    Page<ApplicationResponse> getByJobId(Long jobId, Pageable pageable);

    Page<ApplicationResponse> getByStatus(String status, Pageable pageable);
}
