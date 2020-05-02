package com.example.easyschool.controller;

import com.example.easyschool.domain.Project;
import com.example.easyschool.domain.User;
import com.example.easyschool.domain.Views;
import com.example.easyschool.repo.UserDetailsRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public UserController(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    @GetMapping
    @JsonView(Views.FullUser.class)
    public List<User> list() {
        return userDetailsRepo.findAll();
    }

    @GetMapping("/allStudents")
    @JsonView(Views.FullUser.class)
    public List<User> getAllStudents() {
        return userDetailsRepo.getAllStudents();
    }

    @PostMapping
    @JsonView(Views.FullUser.class)
    public User create(
            @RequestBody User user,
            @AuthenticationPrincipal User userAuthenticated) {
        return userDetailsRepo.save(user);
    }

}
