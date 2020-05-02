package com.example.easyschool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class MessageWithTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.IdName.class)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.IdName.class)
    private User author;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonView(Views.FullProject.class)
    private Group grp;

    @ManyToMany
    @JoinTable(
            name = "usr_msgTeacher",
            joinColumns = @JoinColumn(name = "messageWithTeacher_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonView(Views.Id.class)
    private List<User> usersWhoSaw;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Group getGroup() {
        return grp;
    }

    public void setGroup(Group group) {
        this.grp = group;
    }

    public Group getGrp() {
        return grp;
    }

    public void setGrp(Group grp) {
        this.grp = grp;
    }

    public List<User> getUsersWhoSaw() {
        return usersWhoSaw;
    }

    public void setUsersWhoSaw(List<User> usersWhoSaw) {
        this.usersWhoSaw = usersWhoSaw;
    }
}
