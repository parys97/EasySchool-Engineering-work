package com.example.easyschool.controller;

import com.example.easyschool.domain.Group;
import com.example.easyschool.domain.MessageWithTeacher;
import com.example.easyschool.domain.User;
import com.example.easyschool.domain.Views;
import com.example.easyschool.repo.MessageWithTeacherRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("messageWithTeacher")
public class MessageWithTeacherController {
    private final MessageWithTeacherRepo messageWithTeacherRepo;

    @Autowired
    public MessageWithTeacherController(MessageWithTeacherRepo messageWithTeacherRepo) {
        this.messageWithTeacherRepo = messageWithTeacherRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<MessageWithTeacher> list() {
        return messageWithTeacherRepo.findAll();
    }

    @GetMapping("/getLast")
    @JsonView(Views.IdName.class)
    public MessageWithTeacher getLast(@RequestParam Long id_group) {
        return messageWithTeacherRepo.getLast(id_group);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProject.class)
    public MessageWithTeacher getOne(@PathVariable("id") MessageWithTeacher messageWithTeacher) {
        return messageWithTeacher;
    }

    @PostMapping
    @JsonView(Views.FullProject.class)
    public MessageWithTeacher create(
            @RequestBody MessageWithTeacher messageWithTeacher,
            @AuthenticationPrincipal User user
    ) {
        messageWithTeacher.setCreationDate(LocalDateTime.now());
        messageWithTeacher.setAuthor(user);
        return messageWithTeacherRepo.save(messageWithTeacher);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullProject.class)
    public MessageWithTeacher update(
            @PathVariable("id") MessageWithTeacher messageWithTeacherFromDb,
            @RequestBody MessageWithTeacher messageWithTeacher
    ) {
        messageWithTeacherFromDb.setUsersWhoSaw(messageWithTeacher.getUsersWhoSaw());
        return messageWithTeacherRepo.save(messageWithTeacherFromDb);
    }
}
