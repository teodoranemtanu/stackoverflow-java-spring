package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.CommunityModel;
import com.project.stackoverflow.model.QuestionModel;
import com.project.stackoverflow.service.CommunityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PutMapping("/communities")
    public void saveCommunity(@RequestBody CommunityModel communityModel) {
        this.communityService.saveCommunity(communityModel);
    }

    @DeleteMapping("/communities/{id}")
    public void removeCommunity(@PathVariable String id) {
        this.communityService.removeCommunity(id);
    }

    @PostMapping("/communities/{communityId}/users/{userId}")
    public void joinCommunity(@PathVariable String communityId, @PathVariable String userId) {
        this.communityService.joinCommunity(communityId, userId);
    }

    @DeleteMapping("/communities/{communityId}/users/{userId}")
    public void leaveCommunity(@PathVariable String communityId, @PathVariable String userId) {
        this.communityService.leaveCommunity(communityId, userId);
    }

    @GetMapping("/communities/{communityId/users}")
    public void getUsersFromCommunity(@PathVariable String communityId) {
        this.communityService.getUsersFromCommunity(communityId);
    }

    @GetMapping("/communities/{communityId}/questions}")
    public List<QuestionModel> getQuestionsFromCommunity(@PathVariable String communityId) {
        return this.communityService.getQuestionsFromCommunity(communityId);
    }
}
