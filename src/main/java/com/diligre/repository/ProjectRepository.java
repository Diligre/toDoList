package com.diligre.repository;

import com.diligre.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findOneById(Long id);

    void deleteAllByIdIn(List<Long> ids);

}
