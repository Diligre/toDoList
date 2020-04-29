package com.diligre.controller;

import com.diligre.dto.BulkDeleteDto;
import com.diligre.entity.Project;
import com.diligre.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }


    @GetMapping
    public List<Project> allData(){
        System.out.println("vaduxa lubut xyi");
        return projectService.getAll();
    }

    @GetMapping("/{id}")
    public Project findOneById(@PathVariable Long id){
        return projectService.findOneById(id);
    }

    @PostMapping
    public List<Project> saveData(@RequestBody List<Project> projects){
        return projectService.saveData(projects);
    }

    @PutMapping
    public List<Project> update(@RequestBody List<Project> projects){
        return projectService.saveData(projects);
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
