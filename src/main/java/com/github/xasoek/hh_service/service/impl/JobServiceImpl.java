package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.dto.CreateJobRequest;
import com.github.xasoek.hh_service.dto.JobResponse;
import com.github.xasoek.hh_service.entity.Job;
import com.github.xasoek.hh_service.entity.Role;
import com.github.xasoek.hh_service.entity.User;
import com.github.xasoek.hh_service.mapper.JobMapper;
import com.github.xasoek.hh_service.repository.JobRepository;
import com.github.xasoek.hh_service.repository.UserRepository;
import com.github.xasoek.hh_service.security.SecurityUtil;
import com.github.xasoek.hh_service.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobServiceImpl(JobRepository jobRepository,
                          UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @Override
    public JobResponse create(CreateJobRequest request) {
        User user = userRepository.findByEmail(
                        SecurityUtil.getCurrentUserEmail()
                )
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRole() != Role.HR) {
            throw new RuntimeException("Only HR can create jobs");
        }
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
