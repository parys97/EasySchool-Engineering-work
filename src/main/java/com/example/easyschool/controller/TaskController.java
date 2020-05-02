package com.example.easyschool.controller;

import com.example.easyschool.domain.Group;
import com.example.easyschool.domain.Task;
import com.example.easyschool.domain.User;
import com.example.easyschool.domain.Views;
import com.example.easyschool.repo.TaskRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {
    private final TaskRepo taskRepo;

    @Autowired
    public TaskController(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @GetMapping
    @JsonView(Views.FullProject.class)
    public List<Task> list() {
        return taskRepo.findAll();
    }

    @GetMapping("/myTasksId")
    @JsonView(Views.FullProject.class)
    public List<Long> listMyTasksId(@AuthenticationPrincipal User user) {
        return taskRepo.findByResponsibleUser(user.getId());
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Task getOne(@PathVariable("id") Task task) {
        return task;
    }

    @PostMapping
    @JsonView(Views.FullProject.class)
    public Task create(@RequestBody Task task) {
        return taskRepo.save(task);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Task update(
            @PathVariable("id") Task taskFromDb,
            @RequestBody Task task
    ) {
        taskFromDb.setResponsibleUsers(task.getResponsibleUsers());
        taskFromDb.setProgress(task.getProgress());
        taskFromDb.setDeadline(task.getDeadline());
        taskFromDb.setNameTask(task.getNameTask());

        return taskRepo.save(taskFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Task task) {
        taskRepo.delete(task);
    }
}
