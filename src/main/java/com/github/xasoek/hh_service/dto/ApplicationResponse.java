package com.github.xasoek.hh_service.dto;


import lombok.Data;

@Data
public class ApplicationResponse {
    private Long id;
    private String status;
    private Long userId;
    private Long jobId;
}
