package com.project.stackoverflow.service;

import com.project.stackoverflow.model.CommunityModel;
import com.project.stackoverflow.model.QuestionModel;
import com.project.stackoverflow.model.UserCommunityModel;
import com.project.stackoverflow.repository.CommunityRepository;
import com.project.stackoverflow.repository.QuestionRepository;
import com.project.stackoverflow.repository.UserCommunityRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CommunityService {
    private CommunityRepository communityRepository;
    private UserCommunityRepository userCommunityRepository;
    private QuestionRepository questionRepository;

    public CommunityService(CommunityRepository communityRepository,
                            UserCommunityRepository userCommunityRepository,
                            QuestionRepository questionRepository) {
        this.communityRepository = communityRepository;
        this.userCommunityRepository = userCommunityRepository;
        this.questionRepository = questionRepository;
    }

    public void saveCommunity(CommunityModel communityModel) {
        this.communityRepository.saveCommunity(communityModel);
    }

    public void removeCommunity(String id) {
        this.communityRepository.removeCommunity(id);
    }

    public void joinCommunity(String communityId, String userId) {
        UserCommunityModel userCommunityModel = new UserCommunityModel(
                communityId,
                userId,
                LocalDateTime.now()
        );
        this.userCommunityRepository.joinCommunity(userCommunityModel);
    }

    public void leaveCommunity(String communityId, String userId) {
        userCommunityRepository.leaveCommunity(communityId, userId);
    }

    public void getUsersFromCommunity(String communityId) {
        this.userCommunityRepository.getUsersFromCommunity(communityId);
    }

    public List<QuestionModel> getQuestionsFromCommunity(String communityId) {
        return this.questionRepository.getQuestions(null, communityId);
    }
}
