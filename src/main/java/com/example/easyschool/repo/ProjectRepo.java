package com.example.easyschool.repo;

import com.example.easyschool.domain.Project;
import com.example.easyschool.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.author = ?1")
    List<Project> findByUser(User user);
}
