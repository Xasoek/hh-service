package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.dto.CreateJobRequest;
import com.github.xasoek.hh_service.dto.JobResponse;
import com.github.xasoek.hh_service.entity.Job;
import com.github.xasoek.hh_service.mapper.JobMapper;
import com.github.xasoek.hh_service.repository.JobRepository;
import com.github.xasoek.hh_service.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public JobResponse create(CreateJobRequest request) {
        Job job = JobMapper.toEntity(request);
        Job savedJob = jobRepository.save(job);
        return JobMapper.toResponse(savedJob);
    }


    @Override
    public List<JobResponse> getAll() {
        return jobRepository.findAll()
                .stream()
                .map(JobMapper::toResponse)
                .toList();
    }

}
