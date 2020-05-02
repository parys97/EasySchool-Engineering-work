package com.example.easyschool.controller;

import com.example.easyschool.domain.Group;
import com.example.easyschool.domain.Project;
import com.example.easyschool.domain.Theme;
import com.example.easyschool.domain.Views;
import com.example.easyschool.repo.ThemeRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("theme")
public class ThemeController {
    private final ThemeRepo themeRepo;

    @Autowired
    public ThemeController(ThemeRepo themeRepo) {
        this.themeRepo = themeRepo;
    }

    @GetMapping
    @JsonView(Views.FullProject.class)
    public List<Theme> list() {
        return themeRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Theme getOne(@PathVariable("id") Theme theme) {
        return theme;
    }

    @PostMapping
    @JsonView(Views.FullProject.class)
    public Theme create(@RequestBody Theme theme) {
        return themeRepo.save(theme);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Theme update(
            @PathVariable("id") Theme themeFromDb,
            @RequestBody Theme theme
    ) {
        themeFromDb.setAvailable(theme.isAvailable());
        themeFromDb.setNameTheme(theme.getNameTheme());

        return themeRepo.save(themeFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Theme theme) {
        themeRepo.delete(theme);
    }
}
