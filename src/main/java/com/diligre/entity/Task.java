package com.diligre.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "id_seq",allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


}
