package com.example.easyschool.domain;

import com.example.easyschool.dto.EntityIdResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;


@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = EntityIdResolver.class,
        scope=Group.class
)
@Table(name = "grp")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @OneToOne
    @JoinColumn(name = "theme_id")
    @JsonView(Views.IdName.class)
    private Theme theme;

    @ManyToOne
    @JoinColumn(name = "responsible_user_id")
    @JsonView(Views.FullProject.class)
    private User responsibleUser;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonView(Views.FullProject.class)
    private Project project;

    @OneToMany (mappedBy = "grp", orphanRemoval = true)
    @JsonView(Views.FullProject.class)
    private List<Message> messages;

    @OneToMany (mappedBy = "grp", orphanRemoval = true)
    @JsonView(Views.FullProject.class)
    private List<MessageWithTeacher> messagesWithTeacher;

    @OneToOne
    @JoinColumn(name = "finalFile_id")
    @JsonView(Views.IdName.class)
    private FileModel finalFile;

    @OneToOne
    @JoinColumn(name = "evaluation_id")
    @JsonView(Views.IdName.class)
    private Evaluation evaluation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usr_grp",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonView(Views.FullProject.class)
    private List<User> participants;

    @OneToMany(mappedBy = "group", orphanRemoval = true)
    @JsonView(Views.FullProject.class)
    private List<Task> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<MessageWithTeacher> getMessagesWithTeacher() {
        return messagesWithTeacher;
    }

    public void setMessagesWithTeacher(List<MessageWithTeacher> messagesWithTeacher) {
        this.messagesWithTeacher = messagesWithTeacher;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public User getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(User responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public FileModel getFinalFile() {
        return finalFile;
    }

    public void setFinalFile(FileModel finalFile) {
        this.finalFile = finalFile;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
