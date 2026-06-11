package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.entity.Job;
import com.github.xasoek.hh_service.entity.JobApplication;
import com.github.xasoek.hh_service.repository.JobApplicationRepository;
import com.github.xasoek.hh_service.service.JobApplicationService;

import java.util.List;

public class JobApplicationServiceImpl implements JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Override
    public JobApplication create(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    @Override
    public List<JobApplication> getAll() {
        return jobApplicationRepository.findAll();
    }
}
