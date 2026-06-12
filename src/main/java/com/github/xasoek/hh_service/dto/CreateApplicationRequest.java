package com.github.xasoek.hh_service.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateApplicationRequest {

    @NotNull
    public Long userId;

    @NotNull
    public Long jobId;
}
