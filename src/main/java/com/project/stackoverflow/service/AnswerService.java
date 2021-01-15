package com.project.stackoverflow.service;

import com.project.stackoverflow.exception.AnswerException;
import com.project.stackoverflow.model.AnswerModel;
import com.project.stackoverflow.model.VoteModel;
import com.project.stackoverflow.repository.AnswerRepository;
import com.project.stackoverflow.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final VoteRepository voteRepository;

    public AnswerService(AnswerRepository answerRepository,
                         VoteRepository voteRepository) {
        this.answerRepository = answerRepository;
        this.voteRepository = voteRepository;
    }

    public List<AnswerModel> getAnswers(String questionId, String userId, String filterBy, String sortBy) {
        List<AnswerModel> answers = answerRepository.getAnswers(questionId, userId);
        List<AnswerModel> results;
        if (filterBy != null) {
            results = answers.stream().filter(x -> x.getBody().contains(filterBy)).collect(Collectors.toList());
        } else results = answers;

        for (AnswerModel answer : results) {
            List<VoteModel> votes = voteRepository.getVotes(answer.getId(), userId);
            answer.setVoteCount(votes.size());
        }

        if (sortBy != null && sortBy.equals("-createdAt")) {
            Collections.sort(results, new Comparator<AnswerModel>() {
                public int compare(AnswerModel a, AnswerModel b) {
                    return b.getCreatedAt().compareTo(a.getCreatedAt());
                }
            });
        } else if (sortBy != null && sortBy.equals("body")) {
            Collections.sort(results, new Comparator<AnswerModel>() {
                public int compare(AnswerModel a, AnswerModel b) {
                    return b.getBody().compareTo(a.getBody());
                }
            });
        } else {
            Collections.sort(results, new Comparator<AnswerModel>() {
                public int compare(AnswerModel a, AnswerModel b) {
                    return b.getVoteCount() - a.getVoteCount();
                }
            });
        }
        return results;
    }

    public AnswerModel getAnswerById(String id) {
        Optional<AnswerModel> answer = answerRepository.getAnswerById(id);
        if (!answer.isPresent()) {
            throw AnswerException.answerNotFound();
        }
        List<VoteModel> votes = voteRepository.getVotes(answer.get().getId(), null);
        answer.get().setVoteCount(votes.size());
        return answer.get();
    }

    @Transactional
    public void saveAnswer(AnswerModel answerModel) {
        if (answerModel.getCreatedAt() == null) {
            answerModel.setCreatedAt(LocalDateTime.now());
        }
        Optional<AnswerModel> existingAnswer = answerRepository.getAnswerById(answerModel.getId());
        if (existingAnswer.isPresent()) {
            throw AnswerException.answerAlreadyExists();
        }
        answerRepository.saveAnswer(answerModel);
    }

    @Transactional
    public void removeAnswer(String id) {
        if (!answerRepository.removeAnswer(id)) {
            throw AnswerException.answerCouldNotBeRemoved();
        }
    }

    @Transactional
    public void voteAnswer(String answerId, String userId) {
        VoteModel voteModel = new VoteModel();
        voteModel.setAnswerId(answerId);
        voteModel.setUserId(userId);
        if (!voteRepository.saveVote(voteModel)) {
            throw AnswerException.voteCouldNotBeRemoved();
        }
        ;
    }

    @Transactional
    public void unvoteAnswer(String answerId, String userId) {
        if (!voteRepository.removeVote(answerId, userId)) {
            throw AnswerException.voteCouldNotBeRemoved();
        }
    }

    public List<VoteModel> getAnswerVotes(String answerId) {
        return voteRepository.getVotes(answerId, null);
    }
}
