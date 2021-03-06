package com.diligre.controller;


import com.diligre.dto.*;
import com.diligre.entity.Task;
import com.diligre.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> allData(){
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public Task findOneById(@PathVariable Long id){
        return taskService.findOneById(id);
    }

    @GetMapping("/byProject/{projectId}")
    public List<Task> findAllByProjectIdOrderByPriorityAsc(@PathVariable Long projectId){
        return taskService.findAllByProjectIdOrderByPriorityAsc(projectId);
    }

    @PutMapping("/priority")
    public Task updatePriority(@RequestBody UpdatePriorityDto updatePriorityDto){
        return taskService.updatePriority(updatePriorityDto);
    }

    @PostMapping
    public Task saveData(@RequestBody SaveTaskDto saveTaskDtos){
        return taskService.saveData(saveTaskDtos);
    }

    @PutMapping
    public Task updateData(@RequestBody UpdateTastDto updateTastDtos){
        return taskService.updateData(updateTastDtos);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }

    @DeleteMapping
    public ResponseEntity<String> bulkDelete(@RequestBody BulkDeleteDto bulkDeleteDto){
        taskService.bulkDelete(bulkDeleteDto.getIds());
        return ResponseEntity.ok("delete"+bulkDeleteDto.getIds());
    }

    @PutMapping("/updateStatus")
    public Task updateStatusTask(@RequestBody UpdateStatusTaskDto updateStatusTaskDto){
       return taskService.updateStatusTask(updateStatusTaskDto);
    }
}
