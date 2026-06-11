package com.github.xasoek.hh_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.SENT;
}
