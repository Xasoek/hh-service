package com.github.xasoek.hh_service.controller;

import com.github.xasoek.hh_service.entity.Job;
import com.github.xasoek.hh_service.repository.JobRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @PostMapping
    public Job create(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    @GetMapping
    public List<Job> getAll() {
        return jobRepository.findAll();
    }

}
