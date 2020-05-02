package com.example.easyschool.repo;

import com.example.easyschool.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    @Query(value = "SELECT id FROM task WHERE id in (SELECT task_id FROM usr_task WHERE user_id = ?1)" , nativeQuery = true)
    List<Long> findByResponsibleUser(Long user_id);
}
