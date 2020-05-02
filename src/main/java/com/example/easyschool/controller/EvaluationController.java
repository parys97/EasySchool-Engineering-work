package com.example.easyschool.controller;

import com.example.easyschool.domain.Evaluation;
import com.example.easyschool.domain.Views;
import com.example.easyschool.repo.EvaluationRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("evaluation")
public class EvaluationController {
    private final EvaluationRepo evaluationRepo;

    @Autowired
    public EvaluationController(EvaluationRepo evaluationRepo) {
        this.evaluationRepo = evaluationRepo;
    }

    @GetMapping
    @JsonView(Views.FullProject.class)
    public List<Evaluation> list() {
        return evaluationRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Evaluation getOne(@PathVariable("id") Evaluation evaluation) {
        return evaluation;
    }

    @PostMapping
    @JsonView(Views.FullProject.class)
    public Evaluation create(@RequestBody Evaluation evaluation) {
        return evaluationRepo.save(evaluation);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Evaluation update(
            @PathVariable("id") Evaluation evaluationFromDb,
            @RequestBody Evaluation evaluation
    ) {
        evaluationFromDb.setEvaluation(evaluation.getEvaluation());
        evaluationFromDb.setComment(evaluation.getComment());

        return evaluationRepo.save(evaluationFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Evaluation evaluation) {
        evaluationRepo.delete(evaluation);
    }
}
