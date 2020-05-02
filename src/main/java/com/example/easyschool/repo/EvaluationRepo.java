package com.example.easyschool.repo;

import com.example.easyschool.domain.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepo extends JpaRepository<Evaluation, Long> {

}
