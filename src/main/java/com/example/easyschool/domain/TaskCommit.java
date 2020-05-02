package com.example.easyschool.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class TaskCommit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String comment;

    @OneToOne
    @JoinColumn(name = "file_id")
    @JsonView(Views.IdName.class)
    private FileModel file;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonView(Views.FullProject.class)
    private Task task;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yy hh:MM:ss")
    @JsonView(Views.FullProject.class)
    private LocalDateTime taskCommitTime;

    @JsonView(Views.FullProject.class)
    private Integer progress=0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDateTime getTaskCommitTime() {
        return taskCommitTime;
    }

    public void setTaskCommitTime(LocalDateTime taskCommitTime) {
        this.taskCommitTime = taskCommitTime;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public FileModel getFile() {
        return file;
    }

    public void setFile(FileModel file) {
        this.file = file;
    }
}
