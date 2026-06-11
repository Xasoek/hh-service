package com.github.xasoek.hh_service.service;

import com.github.xasoek.hh_service.dto.CreateJobRequest;
import com.github.xasoek.hh_service.dto.JobResponse;

import java.util.List;

public interface JobService {
    JobResponse create(CreateJobRequest job);
    List<JobResponse> getAll();
}
