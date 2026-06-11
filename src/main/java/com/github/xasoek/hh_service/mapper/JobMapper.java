package com.github.xasoek.hh_service.mapper;

import com.github.xasoek.hh_service.dto.CreateJobRequest;
import com.github.xasoek.hh_service.dto.JobResponse;
import com.github.xasoek.hh_service.entity.Job;

public class JobMapper {
    public static Job toEntity(CreateJobRequest request) {
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        return job;
    }

    public static JobResponse toResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setCompany(job.getCompany());
        response.setSalary(job.getSalary());
        return response;
    }

}
