package com.github.xasoek.hh_service.service;


import com.github.xasoek.hh_service.entity.JobApplication;

import java.util.List;

public interface JobApplicationService {
    JobApplication create(JobApplication jobApplication);
    List<JobApplication> getAll();
}
