package com.github.xasoek.hh_service.dto;

import lombok.Data;

@Data
public class JobResponse {
    private Long id;
    private String title;
    private String company;
    private Integer salary;
}
