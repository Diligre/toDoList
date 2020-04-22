package com.diligre.repository;

import com.diligre.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    Task findOneById(Long id);

    void deleteAllByIdIn(List<Long> ids);

}
