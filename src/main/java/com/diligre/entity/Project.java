package com.diligre.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name ="project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "id_seq",allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Task> tasks;



}
