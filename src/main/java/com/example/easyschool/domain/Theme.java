package com.example.easyschool.domain;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String nameTheme;

    @JsonView(Views.IdName.class)
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonView(Views.FullProject.class)
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameTheme() {
        return nameTheme;
    }

    public void setNameTheme(String nameTheme) {
        this.nameTheme = nameTheme;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
