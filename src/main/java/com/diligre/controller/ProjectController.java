package com.diligre.controller;

import com.diligre.dto.BulkDeleteDto;
import com.diligre.entity.Project;
import com.diligre.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }


    @GetMapping
    public List<Project> allData(){
        return projectService.getAll();
    }

    @GetMapping("/{id}")
    public Project findOneById(@PathVariable Long id){
        return projectService.findOneById(id);
    }

    @PostMapping
    public Project saveData(@RequestBody Project project){
        return projectService.saveData(project);
    }

    @PutMapping
    public Project update(@RequestBody Project project){
        return projectService.saveData(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        projectService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @DeleteMapping
    public ResponseEntity<String> bulkDelete(@RequestBody BulkDeleteDto bulkDeleteDto){
        projectService.bulkDelete(bulkDeleteDto.getIds());
        return ResponseEntity.ok("deleted" + bulkDeleteDto.getIds());
    }

}
