package com.example.easyschool.domain;

import com.example.easyschool.dto.EntityIdResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = EntityIdResolver.class,
        scope=Task.class
)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String nameTask;

    @JsonView(Views.IdName.class)
    private Integer progress = 0;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonView(Views.FullProject.class)
    private Group group;

    @OneToMany (mappedBy = "task", orphanRemoval = true)
    @JsonView(Views.FullProject.class)
    private List<TaskCommit> taskCommits;

    @ManyToMany
    @JoinTable(
            name = "usr_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonView(Views.FullProject.class)
    private List<User> responsibleUsers;

    @JsonView(Views.FullProject.class)
    private LocalDate deadline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<User> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(List<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public List<TaskCommit> getTaskCommits() {
        return taskCommits;
    }

    public void setTaskCommits(List<TaskCommit> taskCommits) {
        this.taskCommits = taskCommits;
    }
}
