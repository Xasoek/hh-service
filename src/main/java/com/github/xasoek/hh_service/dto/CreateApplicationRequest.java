package com.github.xasoek.hh_service.dto;

public class CreateApplicationRequest {
    private Long userId;
    private Long jobId;

    public Long getJobId() {
        return jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
