package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.entity.ApplicationStatus;
import com.github.xasoek.hh_service.entity.Job;
import com.github.xasoek.hh_service.entity.JobApplication;
import com.github.xasoek.hh_service.entity.User;
import com.github.xasoek.hh_service.exception.ApplicationNotFoundException;
import com.github.xasoek.hh_service.exception.JobNotFoundException;
import com.github.xasoek.hh_service.exception.UserNotFoundException;
import com.github.xasoek.hh_service.repository.JobApplicationRepository;
import com.github.xasoek.hh_service.repository.JobRepository;
import com.github.xasoek.hh_service.repository.UserRepository;
import com.github.xasoek.hh_service.service.JobApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public JobApplicationServiceImpl(
            JobApplicationRepository jobApplicationRepository,
            UserRepository userRepository,
            JobRepository jobRepository) {

        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public JobApplication create(Long userId, Long jobId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + jobId));

        boolean alreadyApplied = jobApplicationRepository
                .existsByUserIdAndJobId(userId, jobId);

        if (alreadyApplied) {
            throw new IllegalStateException("User already applied for this job");
        }

        JobApplication application = new JobApplication();
        application.setUser(user);
        application.setJob(job);
        application.setStatus(ApplicationStatus.SENT);

        return jobApplicationRepository.save(application);
    }

    @Override
    public List<JobApplication> getAll() {
        return jobApplicationRepository.findAll();
    }

    @Override
    public JobApplication updateStatus(Long applicationId, String status) {
        JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));
        jobApplication.setStatus(ApplicationStatus.valueOf(status));
        return jobApplicationRepository.save(jobApplication);
    }
}