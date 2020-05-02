package com.example.easyschool.repo;

import com.example.easyschool.domain.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FileRepository extends JpaRepository<FileModel, Long> {
}
