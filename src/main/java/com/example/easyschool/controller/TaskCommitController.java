package com.example.easyschool.controller;

import com.example.easyschool.domain.TaskCommit;
import com.example.easyschool.domain.Views;
import com.example.easyschool.repo.TaskCommitRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("taskCommit")
public class TaskCommitController {
    private final TaskCommitRepo taskCommitRepo;

    @Autowired
    public TaskCommitController(TaskCommitRepo taskCommitRepo) {
        this.taskCommitRepo = taskCommitRepo;
    }

    @GetMapping
    @JsonView(Views.FullProject.class)
    public List<TaskCommit> list() {
        return taskCommitRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProject.class)
    public TaskCommit getOne(@PathVariable("id") TaskCommit taskCommit) {
        return taskCommit;
    }

    @PostMapping
    @JsonView(Views.FullProject.class)
    public TaskCommit create(@RequestBody TaskCommit taskCommit) {
        taskCommit.setTaskCommitTime(LocalDateTime.now());
        return taskCommitRepo.save(taskCommit);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullProject.class)
    public TaskCommit update(
            @PathVariable("id") TaskCommit taskCommitFromDb,
            @RequestBody TaskCommit taskCommit
    ) {
        return taskCommitRepo.save(taskCommitFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") TaskCommit taskCommit) {
        taskCommitRepo.delete(taskCommit);
    }
}
