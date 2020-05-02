package com.example.easyschool.domain;

import com.example.easyschool.dto.EntityIdResolver;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@Table(name="file_model")
public class FileModel{
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonView(Views.Id.class)
    private Long id;

    @Column(name = "path")
    @JsonView(Views.FullFile.class)
    private String path;

    @Column(name = "name")
    @JsonView(Views.IdName.class)
    private String name;

    @Column(name = "mimetype")
    @JsonView(Views.IdName.class)
    private String mimetype;


    public FileModel(){}

    public FileModel(String path, String mimetype, String name){
        this.path = path;
        this.mimetype = mimetype;
        this.name = name;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimetype(){
        return this.mimetype;
    }

    public void setMimetype(String mimetype){
        this.mimetype = mimetype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

