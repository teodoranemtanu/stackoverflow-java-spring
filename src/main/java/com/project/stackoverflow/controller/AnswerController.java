package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.AnswerModel;
import com.project.stackoverflow.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/answers")
    @ResponseBody
    public List<AnswerModel> getAnswers(@RequestParam(required = false) String questionId, @RequestParam(required = false) String userId) {
        return answerService.getAnswers(questionId, userId);
    }

    @GetMapping("/answers/{id}")
    @ResponseBody
    public AnswerModel getAnswersById(@PathVariable String id) {
        return answerService.getAnswerById(id);
    }

    @PutMapping("/answers")
    public void saveAnswer(@RequestBody AnswerModel answerModel) {
        answerService.saveAnswer(answerModel);
    }

    @DeleteMapping("/answers/{id}")
    public void removeAnswer(@PathVariable String id) {
        answerService.removeAnswer(id);
    }
}
