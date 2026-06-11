package com.github.xasoek.hh_service.controller;

import com.github.xasoek.hh_service.dto.CreateApplicationRequest;
import com.github.xasoek.hh_service.entity.JobApplication;
import com.github.xasoek.hh_service.service.JobApplicationService;
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
    public JobApplication create(@RequestBody CreateApplicationRequest request) {
        return service.create(request.getUserId(), request.getJobId());
    }

    @GetMapping
    public List<JobApplication> getAll() {
        return service.getAll();
    }
}