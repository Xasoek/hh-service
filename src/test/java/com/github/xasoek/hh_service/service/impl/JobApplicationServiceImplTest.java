package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.dto.ApplicationResponse;
import com.github.xasoek.hh_service.entity.ApplicationStatus;
import com.github.xasoek.hh_service.entity.Job;
import com.github.xasoek.hh_service.entity.JobApplication;
import com.github.xasoek.hh_service.entity.Role;
import com.github.xasoek.hh_service.entity.User;
import com.github.xasoek.hh_service.exception.DuplicateApplicationException;
import com.github.xasoek.hh_service.repository.JobApplicationRepository;
import com.github.xasoek.hh_service.repository.JobRepository;
import com.github.xasoek.hh_service.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceImplTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobApplicationServiceImpl service;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createUsesAuthenticatedUser() {
        User user = user(10L, "user@example.com", Role.USER);
        Job job = job(20L);
        authenticate(user.getEmail());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));
        when(jobApplicationRepository.existsByUserIdAndJobId(user.getId(), job.getId())).thenReturn(false);
        when(jobApplicationRepository.save(any(JobApplication.class))).thenAnswer(invocation -> {
            JobApplication application = invocation.getArgument(0);
            application.setId(30L);
            return application;
        });

        ApplicationResponse response = service.create(job.getId());

        assertThat(response.getId()).isEqualTo(30L);
        assertThat(response.getUserId()).isEqualTo(user.getId());
        assertThat(response.getJobId()).isEqualTo(job.getId());
        assertThat(response.getStatus()).isEqualTo(ApplicationStatus.SENT.name());
        verify(jobApplicationRepository).save(any(JobApplication.class));
    }

    @Test
    void createRejectsDuplicateApplication() {
        User user = user(10L, "user@example.com", Role.USER);
        Job job = job(20L);
        authenticate(user.getEmail());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));
        when(jobApplicationRepository.existsByUserIdAndJobId(user.getId(), job.getId())).thenReturn(true);

        assertThatThrownBy(() -> service.create(job.getId()))
                .isInstanceOf(DuplicateApplicationException.class);
    }

    @Test
    void getAllRequiresHrRole() {
        User user = user(10L, "user@example.com", Role.USER);
        authenticate(user.getEmail());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> service.getAll())
                .isInstanceOf(AccessDeniedException.class);
    }

    private void authenticate(String email) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES)
        );
    }

    private User user(Long id, String email, Role role) {
        User user = new User();
        user.setId(id);
        user.setName("Test User");
        user.setEmail(email);
        user.setPassword("encoded-password");
        user.setRole(role);
        return user;
    }

    private Job job(Long id) {
        Job job = new Job();
        job.setId(id);
        job.setTitle("Java Developer");
        job.setCompany("Acme");
        job.setSalary(1000);
        return job;
    }
}
