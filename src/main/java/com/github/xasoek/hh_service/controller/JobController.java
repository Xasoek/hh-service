package com.github.xasoek.hh_service.controller;

import com.github.xasoek.hh_service.dto.CreateJobRequest;
import com.github.xasoek.hh_service.dto.JobResponse;
import com.github.xasoek.hh_service.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @PostMapping
    public JobResponse create(@RequestBody CreateJobRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<JobResponse> getAll() {
        return service.getAll();
    }
}