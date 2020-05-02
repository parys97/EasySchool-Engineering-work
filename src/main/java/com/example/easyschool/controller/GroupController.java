package com.example.easyschool.controller;

import com.example.easyschool.domain.Evaluation;
import com.example.easyschool.domain.Group;
import com.example.easyschool.domain.User;
import com.example.easyschool.domain.Views;
import com.example.easyschool.repo.EvaluationRepo;
import com.example.easyschool.repo.GroupRepo;
import com.example.easyschool.repo.ProjectRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group")
public class GroupController {
    private final GroupRepo groupRepo;
    private final EvaluationRepo evaluationRepo;

    @Autowired
    public GroupController(GroupRepo groupRepo, EvaluationRepo evaluationRepo) {
        this.groupRepo = groupRepo;
        this.evaluationRepo = evaluationRepo;
    }


    @GetMapping
    @JsonView(Views.FullProject.class)
    public List<Group> list() {
        return groupRepo.findAll();
    }

    @GetMapping("/myStudentsGroups")
    @JsonView(Views.FullProject.class)
    public List<Group> listMyStudentsGroups(@AuthenticationPrincipal User user) {
        return groupRepo.findByAuthor(user);
    }

    @GetMapping("/myGroupsId")
    @JsonView(Views.FullProject.class)
    public List<Long> listMyGroupsId(@AuthenticationPrincipal User user) {
        return groupRepo.findByParticipant(user.getId());
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Group getOne(@PathVariable("id") Group group) {
        return group;
    }

    @PostMapping
    @JsonView(Views.FullProject.class)
    public Group create(@RequestBody Group group) {
        return groupRepo.save(group);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Group update(
            @PathVariable("id") Group groupFromDb,
            @RequestBody Group group
    ) {
        groupFromDb.setTheme(group.getTheme());
        groupFromDb.setParticipants(group.getParticipants());
        groupFromDb.setResponsibleUser(group.getResponsibleUser());
        groupFromDb.setFinalFile(group.getFinalFile());
        groupFromDb.setEvaluation(group.getEvaluation());

        return groupRepo.save(groupFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Group group) {
        groupRepo.delete(group);
    }
}
