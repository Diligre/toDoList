package com.diligre.service;


import com.diligre.entity.Project;
import com.diligre.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    public Project findOneById(Long id){
        return projectRepository.findOneById(id);
    }

    @Transactional
    public void bulkDelete(List<Long> ids){
        projectRepository.deleteAllByIdIn(ids);
    }

    @Transactional
    public List<Project> saveData(List<Project> projects){
        return projectRepository.saveAll(projects);
    }


}
