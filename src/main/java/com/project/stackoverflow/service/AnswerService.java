package com.project.stackoverflow.service;

import com.project.stackoverflow.model.AnswerModel;
import com.project.stackoverflow.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerService {
    private AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<AnswerModel> getAnswers(String questionId, String userId) {
        return answerRepository.getAnswers(questionId, userId);
    }

    public AnswerModel getAnswerById(String id) {
        return answerRepository.getAnswerById(id);
    }

    @Transactional
    public void saveAnswer(AnswerModel answerModel) {
        answerRepository.saveAnswer(answerModel);
    }

    @Transactional
    public void removeAnswer(String id) {
        answerRepository.removeAnswer(id);
    }
}
