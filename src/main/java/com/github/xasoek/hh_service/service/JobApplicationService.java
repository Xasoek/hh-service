package com.github.xasoek.hh_service.service;


import com.github.xasoek.hh_service.entity.JobApplication;

import java.util.List;

public interface JobApplicationService {
    List<JobApplication> getAll();
    JobApplication create(Long userId, Long jobId);
    JobApplication updateStatus(Long applicationId, String status);

}
