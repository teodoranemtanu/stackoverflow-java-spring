package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.QuestionModel;
import com.project.stackoverflow.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    @ResponseBody
    public List<QuestionModel> getQuestions(@RequestParam(required = false) String userId, @RequestParam(required = false) String communityId) {
        return questionService.getQuestions(userId, communityId);
    }

    @PutMapping("/questions")
    public void addQuestion(@RequestBody QuestionModel questionModel) {
        questionService.saveQuestion(questionModel);
    }

    @DeleteMapping("/questions/{id}")
    public void removeQuestion(@PathVariable String id) {
        questionService.removeQuestion(id);
    }

    @GetMapping("/questions/{id}")
    public QuestionModel getQuestionById(@PathVariable String id) {
        return questionService.getQuestionById(id);
    }
}
