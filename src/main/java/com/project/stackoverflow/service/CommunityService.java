package com.project.stackoverflow.service;

import com.project.stackoverflow.exception.CommunityException;
import com.project.stackoverflow.model.CommunityModel;
import com.project.stackoverflow.model.QuestionModel;
import com.project.stackoverflow.model.UserCommunityModel;
import com.project.stackoverflow.model.UserModel;
import com.project.stackoverflow.repository.CommunityRepository;
import com.project.stackoverflow.repository.QuestionRepository;
import com.project.stackoverflow.repository.UserCommunityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserCommunityRepository userCommunityRepository;
    private final QuestionRepository questionRepository;

    public CommunityService(CommunityRepository communityRepository,
                            UserCommunityRepository userCommunityRepository,
                            QuestionRepository questionRepository) {
        this.communityRepository = communityRepository;
        this.userCommunityRepository = userCommunityRepository;
        this.questionRepository = questionRepository;
    }

    public List<CommunityModel> getCommunities(String id) {
        if(id != null) {
            Optional<CommunityModel> community = communityRepository.getCommunityById(id);
            List<CommunityModel> result = new ArrayList<>();
            if(community.isEmpty()) {
                throw CommunityException.communityNotFound();
            }
            result.add(community.get());
            return result;
        } else return communityRepository.getCommunities();
    }

    public void saveCommunity(CommunityModel communityModel) {
        Optional<CommunityModel> existingCommunity = this.communityRepository.getCommunityById(communityModel.getId());
        if(existingCommunity.isPresent()) {
            throw CommunityException.communityAlreadyExists();
        }
        if(!this.communityRepository.saveCommunity(communityModel)) {
            throw CommunityException.communityCouldNotBeSaved();
        }
    }

    public void removeCommunity(String id) {
        if(!this.communityRepository.removeCommunity(id)){
            throw CommunityException.communityCouldNotBeRemoved();
        }
    }

    public void joinCommunity(String communityId, String userId) {
        UserCommunityModel userCommunityModel = new UserCommunityModel(
                communityId,
                userId,
                LocalDateTime.now()
        );
        List<UserModel> usersFromCommunity = this.userCommunityRepository.getUsersFromCommunity(communityId);
        if(usersFromCommunity.stream().anyMatch(x -> x.getId().equals(userId))) {
            throw CommunityException.userAlreadyInCommunity();
        }
        if(!this.userCommunityRepository.joinCommunity(userCommunityModel)) {
            throw CommunityException.userCouldNotJoinCommunity();
        };
    }

    public void leaveCommunity(String communityId, String userId) {
        if(!userCommunityRepository.leaveCommunity(communityId, userId)) {
            throw CommunityException.userCouldNotLeaveCommunity();
        };
    }

    public List<UserModel> getUsersFromCommunity(String communityId) {
        List<UserModel> users = this.userCommunityRepository.getUsersFromCommunity(communityId);
        if(users.isEmpty()){
            throw CommunityException.communityHasNoUsers();
        }
        return users;
    }

    public List<QuestionModel> getQuestionsFromCommunity(String communityId) {
        List<QuestionModel> questions = questionRepository.getQuestions(null, communityId);
        if(questions.isEmpty()) {
            throw CommunityException.communityHasNoQuestions();
        }
        return questions;
    }
}
