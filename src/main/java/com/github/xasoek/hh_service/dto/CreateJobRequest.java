package com.github.xasoek.hh_service.dto;

import lombok.Data;

@Data
public class CreateJobRequest {

    private String title;

    private String company;

    private String description;

    private Integer salary;
}