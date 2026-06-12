package com.github.xasoek.hh_service.controller;

import com.github.xasoek.hh_service.dto.ApplicationResponse;
import com.github.xasoek.hh_service.dto.CreateApplicationRequest;
import com.github.xasoek.hh_service.entity.JobApplication;
import com.github.xasoek.hh_service.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.xasoek.hh_service.mapper.ApplicationMapper.toResponse;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {
    private final JobApplicationService service;
    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.service = jobApplicationService;
    }
    @PostMapping
    public ApplicationResponse create(@Valid @RequestBody CreateApplicationRequest request) {
        JobApplication app = service.create(request.getUserId(), request.getJobId());
        return toResponse(app);
    }

    @GetMapping
    public List<ApplicationResponse> getAll() {
        return service.getAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ApplicationResponse toResponse(JobApplication app) {
        ApplicationResponse response = new ApplicationResponse();
        response.setId(app.getId());
        response.setStatus(app.getStatus().name());
        response.setUserId(app.getUser().getId());
        response.setJobId((app.getJob().getId()));
        return response;
    }

    @PutMapping("/{id}/status")
    public ApplicationResponse updateStatus(@PathVariable Long id, @RequestParam String status) {
        JobApplication application = service.updateStatus(id, status);
        return toResponse(application);
    }
}