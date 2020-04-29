package com.diligre.service;


import com.diligre.dto.SaveTaskDto;

import com.diligre.dto.UpdateTastDto;
import com.diligre.entity.Project;
import com.diligre.entity.Task;
import com.diligre.repository.ProjectRepository;
import com.diligre.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void delete(Long id) {

        Task task = taskRepository.findOneById(id);

        List<Task> tasks = taskRepository.findAllByProjectIdAndPriorityAfter(task.getProject().getId(), task.getPriority());
        tasks.forEach(t -> t.setPriority(t.getPriority() - 1));

        taskRepository.deleteById(id);
        taskRepository.saveAll(tasks);

    }

    public Task findOneById(Long id) {
        return taskRepository.findOneById(id);
    }

    @Transactional
    public void bulkDelete(List<Long> ids) {
        taskRepository.deleteAllByIdIn(ids);
    }

    @Transactional
    public List<Task> saveData(List<SaveTaskDto> tasks) {
        List<Task> taskList = new ArrayList<>();

        for (SaveTaskDto saveTaskDto : tasks) {

            Task task = new Task();

            task.setId(saveTaskDto.getId());
            task.setName(saveTaskDto.getName());
            task.setStatus(saveTaskDto.getStatus());
            task.setDeadLine(saveTaskDto.getDeadLine());

            Long highestPriorityByProjectId = taskRepository.getHighestPriorityByProjectId(saveTaskDto.getProjectId());
            if (highestPriorityByProjectId == null) {
                task.setPriority(1L);
            } else {
                task.setPriority(highestPriorityByProjectId + 1L);
            }

            task.setProject(projectRepository.findOneById(saveTaskDto.getProjectId()));

            taskList.add(task);
        }
        return taskRepository.saveAll(taskList);
    }

    @Transactional
    public List<Task> updateData(List<UpdateTastDto> tasks) {
        List<Task> taskList = new ArrayList<>();

        for (UpdateTastDto updateTastDto : tasks) {

            Task task = new Task();

            task.setId(updateTastDto.getId());
            task.setName(updateTastDto.getName());
            task.setStatus(updateTastDto.getStatus());

            Long newPriority = updateTastDto.getPriority();
            Long oldPriority = taskRepository.findOneById(updateTastDto.getId()).getPriority();

            List<Task> tasksToUpdatePriority;
            if (newPriority < oldPriority) {
                tasksToUpdatePriority = taskRepository.findAllByProjectIdAndPriorityBetween(
                        updateTastDto.getProjectId(), newPriority, oldPriority - 1);
                tasksToUpdatePriority.forEach(t -> t.setPriority(t.getPriority() + 1));
            } else {
                tasksToUpdatePriority = taskRepository.findAllByProjectIdAndPriorityBetween(
                        updateTastDto.getProjectId(), oldPriority + 1, newPriority);
                tasksToUpdatePriority.forEach(t -> t.setPriority(t.getPriority() - 1));
            }

            taskRepository.saveAll(tasksToUpdatePriority);


            task.setPriority(newPriority);

            task.setDeadLine(updateTastDto.getDeadLine());
            task.setProject(projectRepository.findOneById(updateTastDto.getProjectId()));

            taskList.add(task);
        }
        return taskRepository.saveAll(taskList);
    }


}
