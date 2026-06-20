package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.dto.ApplicationResponse;
import com.github.xasoek.hh_service.entity.ApplicationStatus;
import com.github.xasoek.hh_service.entity.Job;
import com.github.xasoek.hh_service.entity.JobApplication;
import com.github.xasoek.hh_service.entity.Role;
import com.github.xasoek.hh_service.entity.User;
import com.github.xasoek.hh_service.exception.ApplicationNotFoundException;
import com.github.xasoek.hh_service.exception.DuplicateApplicationException;
import com.github.xasoek.hh_service.exception.JobNotFoundException;
import com.github.xasoek.hh_service.exception.UserNotFoundException;
import com.github.xasoek.hh_service.exception.InvalidStatusException;
import com.github.xasoek.hh_service.mapper.ApplicationMapper;
import com.github.xasoek.hh_service.repository.JobApplicationRepository;
import com.github.xasoek.hh_service.repository.JobRepository;
import com.github.xasoek.hh_service.repository.UserRepository;
import com.github.xasoek.hh_service.security.SecurityUtil;
import com.github.xasoek.hh_service.service.JobApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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
    public ApplicationResponse create(Long jobId) {

        User user = getCurrentUser();

        if (user.getRole() != Role.USER) {
            throw new AccessDeniedException("Only users can apply to jobs");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job not found"));

        if (jobApplicationRepository.existsByUserIdAndJobId(user.getId(), job.getId())) {
            throw new DuplicateApplicationException("Application already exists");
        }

        JobApplication app = new JobApplication();
        app.setUser(user);
        app.setJob(job);
        app.setStatus(ApplicationStatus.SENT);

        JobApplication saved = jobApplicationRepository.save(app);

        return ApplicationMapper.toResponse(saved);
    }

    @Override
    public List<ApplicationResponse> getAll() {
        ensureHr();

        return jobApplicationRepository.findAll()
                .stream()
                .map(ApplicationMapper::toResponse)
                .toList();
    }

    @Override
    public ApplicationResponse updateStatus(Long applicationId, String status) {
        ensureHr();

        JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found"));

        ApplicationStatus statusEnum = parseStatus(status);

        jobApplication.setStatus(statusEnum);

        JobApplication saved = jobApplicationRepository.save(jobApplication);

        return ApplicationMapper.toResponse(saved);
    }

    @Override
    public Page<ApplicationResponse> getByUserId(Long userId, Pageable pageable) {
        User currentUser = getCurrentUser();

        if (currentUser.getRole() != Role.HR && !currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("Users can view only their own applications");
        }

        return jobApplicationRepository.findByUserId(userId, pageable)
                .map(ApplicationMapper::toResponse);
    }

    @Override
    public Page<ApplicationResponse> getByJobId(Long jobId, Pageable pageable) {
        ensureHr();

        return jobApplicationRepository.findByJobId(jobId, pageable)
                .map(ApplicationMapper::toResponse);
    }

    @Override
    public Page<ApplicationResponse> getByStatus(String status, Pageable pageable) {
        ensureHr();

        ApplicationStatus statusEnum = parseStatus(status);

        return jobApplicationRepository.findByStatus(statusEnum, pageable)
                .map(ApplicationMapper::toResponse);
    }

    private ApplicationStatus parseStatus(String status) {
        try {
            return ApplicationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidStatusException("Invalid status: " + status);
        }
    }

    private User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void ensureHr() {
        User user = getCurrentUser();

        if (user.getRole() != Role.HR) {
            throw new AccessDeniedException("Only HR can perform this action");
        }
    }
}
