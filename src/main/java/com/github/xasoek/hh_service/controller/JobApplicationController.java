package com.github.xasoek.hh_service.controller;

import com.github.xasoek.hh_service.dto.ApplicationResponse;
import com.github.xasoek.hh_service.dto.CreateApplicationRequest;
import com.github.xasoek.hh_service.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ApplicationResponse create(@Valid @RequestBody CreateApplicationRequest request) {
        return service.create(request.getJobId());
    }

    @GetMapping
    public List<ApplicationResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}/status")
    public ApplicationResponse updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return service.updateStatus(id, status);
    }

    @GetMapping("/user/{userId}")
    public Page<ApplicationResponse> getByUser(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return service.getByUserId(userId, pageable);
    }

    @GetMapping("/job/{jobId}")
    public Page<ApplicationResponse> getByJob(
            @PathVariable Long jobId,
            Pageable pageable
    ) {
        return service.getByJobId(jobId, pageable);
    }
}
