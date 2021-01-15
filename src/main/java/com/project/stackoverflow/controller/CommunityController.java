package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.CommunityModel;
import com.project.stackoverflow.model.QuestionModel;
import com.project.stackoverflow.model.UserModel;
import com.project.stackoverflow.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/communities")
    public ResponseEntity<List<CommunityModel>> getCommunities(@RequestParam(required = false) String id) {
        List<CommunityModel> communities = communityService.getCommunities(id);
        return communities.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(communities);
    }

    @PutMapping("/communities")
    public ResponseEntity<Void> saveCommunity(@RequestBody CommunityModel communityModel) {
        this.communityService.saveCommunity(communityModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/communities/{id}")
    public ResponseEntity<Void> removeCommunity(@PathVariable String id) {
        this.communityService.removeCommunity(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/communities/{communityId}/users/{userId}")
    public ResponseEntity<Void> joinCommunity(@PathVariable String communityId, @PathVariable String userId) {
        this.communityService.joinCommunity(communityId, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/communities/{communityId}/users/{userId}")
    public ResponseEntity<Void> leaveCommunity(@PathVariable String communityId, @PathVariable String userId) {
        this.communityService.leaveCommunity(communityId, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/communities/{communityId}/users")
    public ResponseEntity<List<UserModel>> getUsersFromCommunity(@PathVariable String communityId) {
        List<UserModel> users = this.communityService.getUsersFromCommunity(communityId);
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/communities/{communityId}/questions")
    public ResponseEntity<List<QuestionModel>> getQuestionsFromCommunity(@PathVariable String communityId) {
        List<QuestionModel> questions = this.communityService.getQuestionsFromCommunity(communityId);
        return questions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(questions);
    }
}
