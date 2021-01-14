package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.VoteModel;
import com.project.stackoverflow.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnswerVoteController {
    private final AnswerService answerService;

    public AnswerVoteController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/answers/{answerId}/{userId}/vote")
    public void voteAnswer(@PathVariable String answerId, @PathVariable String userId) {
        answerService.voteAnswer(answerId, userId);
    }

    @DeleteMapping("/answers/{answerId}/{userId}/vote")
    public void unvoteAnswer(@PathVariable String answerId, @PathVariable String userId) {
        answerService.unvoteAnswer(answerId, userId);
    }

    @GetMapping("answers/{id}/vote")
    public List<VoteModel> getAnswerVotes(@PathVariable String id) {
        return answerService.getAnswerVotes(id);
    }

}
