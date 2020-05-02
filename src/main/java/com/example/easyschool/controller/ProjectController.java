package com.example.easyschool.controller;

import com.example.easyschool.domain.Project;
import com.example.easyschool.domain.User;
import com.example.easyschool.domain.Views;
import com.example.easyschool.repo.ProjectRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectController(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    @GetMapping
    @JsonView(Views.FullProject.class)
    public List<Project> list() {
        return projectRepo.findAll();
    }

    @GetMapping("/myProjects")
    @JsonView(Views.FullProject.class)
    public List<Project> listMyProjects(@AuthenticationPrincipal User user) {
        return projectRepo.findByUser(user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Project getOne(@PathVariable("id") Project project) {
        return project;
    }

    @PostMapping
    @JsonView(Views.FullProject.class)
    public Project create(
            @RequestBody Project project,
            @AuthenticationPrincipal User user) {
        project.setAuthor(user);
        return projectRepo.save(project);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Project update(
            @PathVariable("id") Project projectFromDb,
            @RequestBody Project project
    ) {

        projectFromDb.setThematics(project.getThematics());
        projectFromDb.setRequirement(project.getRequirement());
        projectFromDb.setTerm(project.getTerm());

        return projectRepo.save(projectFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Project project) {
        projectRepo.delete(project);
    }
}
