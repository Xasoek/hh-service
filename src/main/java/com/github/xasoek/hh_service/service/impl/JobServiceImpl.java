package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.entity.Job;
import com.github.xasoek.hh_service.repository.JobRepository;
import com.github.xasoek.hh_service.service.JobService;

import java.util.List;

public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job create(Job job) {
        return jobRepository.save(job);
    }


    @Override
    public List<Job> getAll() {
        return jobRepository.findAll();
    }

}
