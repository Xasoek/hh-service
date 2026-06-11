package com.github.xasoek.hh_service.service.impl;

import com.github.xasoek.hh_service.entity.Job;
import com.github.xasoek.hh_service.repository.JobRepositry;
import com.github.xasoek.hh_service.service.JobService;

import java.util.List;

public class JobServiceImpl implements JobService {
    private final JobRepositry jobRepositry;

    public JobServiceImpl(JobRepositry jobRepositry) {
        this.jobRepositry = jobRepositry;
    }

    @Override
    public Job create(Job job) {
        return jobRepositry.save(job);
    }


    @Override
    public List<Job> getAll() {
        return jobRepositry.findAll();
    }

}
