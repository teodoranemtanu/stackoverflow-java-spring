package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.QuestionModel;
import com.project.stackoverflow.model.TagModel;
import com.project.stackoverflow.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<QuestionModel>> getQuestions(@RequestParam(required = false) String userId,
                                                           @RequestParam(required = false) String communityId,
                                                           @RequestParam(required = false) String searchText) {
        List<QuestionModel> questions = questionService.getQuestions(userId, communityId, searchText);
        return questions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(questions);
    }

    @PutMapping("/questions")
    public ResponseEntity<Void> addQuestion(@RequestBody QuestionModel questionModel) {
        questionService.saveQuestion(questionModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> removeQuestion(@PathVariable String id) {
        questionService.removeQuestion(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<QuestionModel> getQuestionById(@PathVariable String id) {
        QuestionModel question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/questions/{id}/tags")
    public ResponseEntity<List<TagModel>> getQuestionTags(@PathVariable String id) {
        List<TagModel> tags = questionService.getQuestionTags(id);
        return tags.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tags);
    }

    @PostMapping("/questions/tags")
    public ResponseEntity<Void> addQuestionTag(@RequestBody TagModel tagModel) {
        questionService.addQuestionTag(tagModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/questions/tags/{tagId}")
    public ResponseEntity<Void> removeQuestionTag(@PathVariable String tagId) {
        questionService.removeQuestionTag(tagId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
