package com.diligre.service;


import com.diligre.dto.SaveTaskDto;

import com.diligre.dto.UpdatePriorityDto;
import com.diligre.dto.UpdateStatusTaskDto;
import com.diligre.dto.UpdateTastDto;
import com.diligre.entity.Task;
import com.diligre.repository.ProjectRepository;
import com.diligre.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

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

        List<Task> tasks;
        if (task.getPriority() != 0) {
            tasks = taskRepository.findAllByProjectIdAndPriorityAfter(task.getProject().getId(), task.getPriority());
            tasks.forEach(t -> t.setPriority(t.getPriority() - 1));
            taskRepository.saveAll(tasks);
        }
        taskRepository.deleteById(id);
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

            task.setName(saveTaskDto.getName());
            task.setStatus(saveTaskDto.getStatus());

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
    public Task updateData(UpdateTastDto updateTastDto) {
        Task task = findOneById(updateTastDto.getId());

        task.setName(updateTastDto.getName());
        task.setDeadLine(updateTastDto.getDeadLine());
        task.setProject(projectRepository.findOneById(task.getProject().getId()));

        return taskRepository.save(task);
    }

    @Transactional
    public Task updatePriority(UpdatePriorityDto updatePriorityDto) {

        Task task = taskRepository.findOneById(updatePriorityDto.getId());
        Long newPriority = updatePriorityDto.getPriority();
        Long oldPriority = task.getPriority();

        List<Task> tasksToUpdatePriority;
        if (newPriority < oldPriority) {
            tasksToUpdatePriority = taskRepository.findAllByProjectIdAndPriorityBetween(
                    task.getProject().getId(), newPriority, oldPriority - 1);
            tasksToUpdatePriority.forEach(t -> t.setPriority(t.getPriority() + 1));
        } else {
            tasksToUpdatePriority = taskRepository.findAllByProjectIdAndPriorityBetween(
                    task.getProject().getId(), oldPriority + 1, newPriority);
            tasksToUpdatePriority.forEach(t -> t.setPriority(t.getPriority() - 1));
        }

        task.setPriority(updatePriorityDto.getPriority());

        taskRepository.saveAll(tasksToUpdatePriority);
        return task;
    }

    @Transactional
    public List<Task> findAllByProjectIdOrderByPriorityAsc(Long projectId) {
        return taskRepository.findAllByProjectIdOrderByPriorityAsc(projectId);
    }

    @Transactional
    public Task updateStatusTask(UpdateStatusTaskDto updateStatusTaskDto) {
        Task task = taskRepository.findOneById(updateStatusTaskDto.getId());

        if (updateStatusTaskDto.getStatus().equals(true)) {
            task.setStatus(updateStatusTaskDto.getStatus());
            task.setPriority(0L);
            List<Task> tasksToUpdatePriority;
            tasksToUpdatePriority = taskRepository.findAllByProjectIdAndPriorityAfter(task.getProject().getId(), task.getPriority());
            tasksToUpdatePriority.forEach(t -> t.setPriority(t.getPriority() - 1));
            taskRepository.saveAll(tasksToUpdatePriority);
        } else {
            task.setStatus(updateStatusTaskDto.getStatus());
            Long highestPriorityByProjectId = taskRepository.getHighestPriorityByProjectId(task.getProject().getId());
            if (highestPriorityByProjectId != null &&
                    highestPriorityByProjectId != 0L) {
                task.setPriority(highestPriorityByProjectId + 1);
            } else {
                task.setPriority(1L);
            }
        }
        return taskRepository.save(task);
    }
}
