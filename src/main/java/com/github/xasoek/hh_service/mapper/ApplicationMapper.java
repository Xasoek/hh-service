package com.github.xasoek.hh_service.mapper;

import com.github.xasoek.hh_service.dto.ApplicationResponse;
import com.github.xasoek.hh_service.entity.JobApplication;

public class ApplicationMapper {
    public static ApplicationResponse toResponse(JobApplication app) {
        ApplicationResponse response = new ApplicationResponse();
        response.setId(app.getId());
        response.setStatus(app.getStatus().name());
        response.setUserId(app.getUser().getId());
        response.setJobId(app.getJob().getId());
        return response;
    }
}
