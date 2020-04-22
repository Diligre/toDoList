package com.diligre.service;


import com.diligre.entity.Task;
import com.diligre.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    public Task findOneById(Long id){
        return taskRepository.findOneById(id);
    }

    @Transactional
    public void bulkDelete(List<Long> ids){
        taskRepository.deleteAllByIdIn(ids);
    }

    @Transactional
    public List<Task> saveData(List<Task> tasks){
       return taskRepository.saveAll(tasks);
    }

}
