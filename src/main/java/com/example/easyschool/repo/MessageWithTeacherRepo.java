package com.example.easyschool.repo;

import com.example.easyschool.domain.MessageWithTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageWithTeacherRepo extends JpaRepository<MessageWithTeacher, Long> {
    @Query(value = "SELECT * FROM message_with_teacher WHERE group_id = ?1 ORDER BY Id DESC limit 1" , nativeQuery = true)
    MessageWithTeacher getLast(Long id_group);
}
