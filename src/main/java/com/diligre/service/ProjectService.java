package com.diligre.service;


import com.diligre.dto.SaveTaskDto;
import com.diligre.entity.Project;
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
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    @Transactional
    public void delete(Long id){
        projectRepository.deleteById(id);
    }


    public Project findOneById(Long id){
        return projectRepository.findOneById(id);
    }

    @Transactional
    public void bulkDelete(List<Long> ids){
        projectRepository.deleteAllByIdIn(ids);
    }

    @Transactional
    public Project saveData(Project projects){
        return projectRepository.save(projects);
    }

}
