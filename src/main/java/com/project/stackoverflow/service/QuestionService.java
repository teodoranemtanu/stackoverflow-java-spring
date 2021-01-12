package com.project.stackoverflow.service;

import com.project.stackoverflow.model.QuestionModel;
import com.project.stackoverflow.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionModel> getQuestions(String userId, String communityId) {
        return questionRepository.getQuestions(userId, communityId);
    }

    public QuestionModel getQuestionById(String id) {
        return questionRepository.getQuestionById(id);
    }

    @Transactional
    public void saveQuestion(QuestionModel questionModel) {
        questionRepository.saveQuestion(questionModel);
    }

    @Transactional
    public void removeQuestion(String id) {
        questionRepository.removeQuestion(id);
    }
}
