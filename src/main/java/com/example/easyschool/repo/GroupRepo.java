package com.example.easyschool.repo;

import com.example.easyschool.domain.Group;
import com.example.easyschool.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepo extends JpaRepository<Group, Long> {
    @Query("SELECT g FROM Group g WHERE g.project.author = ?1")
    List<Group> findByAuthor(User user);

    @Query(value = "SELECT id FROM grp WHERE id in (SELECT group_id FROM usr_grp WHERE user_id = ?1)" , nativeQuery = true)
    List<Long> findByParticipant(Long user_id);

}
