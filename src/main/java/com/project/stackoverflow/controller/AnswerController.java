package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.AnswerModel;
import com.project.stackoverflow.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/answers")
    public ResponseEntity<List<AnswerModel>> getAnswers(@RequestParam(required = false) String questionId,
                                        @RequestParam(required = false) String userId,
                                        @RequestParam(required = false) String filterBy,
                                        @RequestParam(required = false) String sortBy) {

        List<AnswerModel> answers = answerService.getAnswers(questionId, userId, filterBy, sortBy);
        return answers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(answers);
    }

    @GetMapping("/answers/{id}")
    public ResponseEntity<AnswerModel> getAnswersById(@PathVariable String id) {
        AnswerModel answer = answerService.getAnswerById(id);
        return ResponseEntity.ok(answer);
    }

    @PutMapping("/answers")
    public ResponseEntity<Void> saveAnswer(@RequestBody AnswerModel answerModel) {
        answerService.saveAnswer(answerModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/answers/{id}")
    public ResponseEntity<Void> removeAnswer(@PathVariable String id) {
        answerService.removeAnswer(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
