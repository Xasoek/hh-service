package com.github.xasoek.hh_service.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateJobRequest {

    @NotBlank
    public String title;

    @NotBlank
    public String company;

    @Min(0)
    public int salary;

    public String description;
}