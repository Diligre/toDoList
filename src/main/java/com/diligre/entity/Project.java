package com.diligre.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name ="project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "project_id_seq")
    @SequenceGenerator(name = "project_id_seq", sequenceName = "project_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Transient
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

}
