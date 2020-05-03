package com.diligre.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.util.Date;


@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "task_id_seq")
    @SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "priority")
    private Long priority;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
