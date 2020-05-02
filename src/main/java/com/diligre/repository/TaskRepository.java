package com.diligre.repository;

import com.diligre.entity.Project;
import com.diligre.entity.Task;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    Task findOneById(Long id);

    Long findFirstPriorityByProjectIdOrderByPriorityDesc(Long projectId);

    void deleteAllByIdIn(List<Long> ids);

    @Query(value = "SELECT priority FROM task WHERE project_id = :project_id ORDER BY priority DESC LIMIT (1)", nativeQuery = true)
    Long getHighestPriorityByProjectId(@Param("project_id") Long projectId);

    @Query(value = "SELECT COUNT(task.id) FROM project right join task on project.id = task.project_id WHERE project.id = :project.id",nativeQuery = true)
    Long getTaskCountByProjectId(@Param("project.id") Long projectId);

    List<Task> findAllByProjectIdAndPriorityAfter(Long projectId, Long priority);

    List<Task> findAllByProjectIdAndPriorityBetween(Long projectId, Long priorityB, Long priorityA);

    List<Task> findAllByProjectIdOrderByPriorityAsc(Long projectId);

    List<Task> findAllByProjectId(Long projectId);

}
