package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.VoteModel;
import com.project.stackoverflow.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnswerVoteController {
    private final AnswerService answerService;

    public AnswerVoteController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/answers/{answerId}/{userId}/votes")
    public ResponseEntity<Void> voteAnswer(@PathVariable String answerId, @PathVariable String userId) {
        answerService.voteAnswer(answerId, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/answers/{answerId}/{userId}/votes")
    public ResponseEntity<Void> unvoteAnswer(@PathVariable String answerId, @PathVariable String userId) {
        answerService.unvoteAnswer(answerId, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("answers/{id}/votes")
    public ResponseEntity<List<VoteModel>> getAnswerVotes(@PathVariable String id) {
        List<VoteModel> votes = answerService.getAnswerVotes(id);
        return votes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(votes);
    }
}
