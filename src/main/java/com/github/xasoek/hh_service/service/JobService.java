package com.github.xasoek.hh_service.service;

import com.github.xasoek.hh_service.entity.Job;
import java.util.List;

public interface JobService {
    Job create(Job job);

    List<Job> getAll();
}
