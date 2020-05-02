package com.example.easyschool.repo;

import com.example.easyschool.domain.TaskCommit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCommitRepo extends JpaRepository<TaskCommit, Long> {
}
