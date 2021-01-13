package com.project.stackoverflow.service;

import com.project.stackoverflow.model.QuestionModel;
import com.project.stackoverflow.model.TagModel;
import com.project.stackoverflow.repository.QuestionRepository;
import com.project.stackoverflow.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

enum SearchType {
    TITLE,
    BODY,
    TAGS
}

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;

    public QuestionService(QuestionRepository questionRepository,
                           TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
    }

    public List<QuestionModel> getQuestions(String userId, String communityId, String searchText) {
        List<QuestionModel> questions = questionRepository.getQuestions(userId, communityId);
        List<QuestionModel> result;
        if (searchText != null) {
            result = this.searchQuestion(searchText, questions);
        } else result = questions;
        return result;
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

    private List<QuestionModel> searchQuestion(String input, List<QuestionModel> questions) {
        List<String> splitInput = Arrays.asList(input.split("\\W")).stream()
                .filter(x -> !x.isEmpty()).collect(Collectors.toList());
        Set<String> noDuplicatesSplitInput = new HashSet<>(splitInput);

        List<QuestionModel> matchingQuestions = this.searchQuestions(noDuplicatesSplitInput, questions);
        return matchingQuestions;
    }

    private List<QuestionModel> searchQuestions(Set<String> splitInput, List<QuestionModel> questions) {
        List<Boolean> titleMatches = questions.stream()
                .map(question -> isMatchingQuestion(splitInput, question, SearchType.TITLE))
                .collect(Collectors.toList());
        List<Boolean> bodyMatches = questions.stream()
                .map(question -> isMatchingQuestion(splitInput, question, SearchType.BODY))
                .collect(Collectors.toList());
        List<Boolean> tagMatches = questions.stream()
                .map(question -> isMatchingQuestion(splitInput, question, SearchType.TAGS))
                .collect(Collectors.toList());

        List<QuestionModel> matchingQuestions = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            if (titleMatches.get(i))
                matchingQuestions.add(questions.get(i));
            else if (bodyMatches.get(i))
                matchingQuestions.add(questions.get(i));
            else if(tagMatches.get(i))
                matchingQuestions.add(questions.get(i));
        }

        return matchingQuestions;
    }

    private Boolean isMatchingQuestion(Set<String> splitInput, QuestionModel question, SearchType searchType) {
        Set<String> splitQuestion;
        if (searchType == SearchType.TITLE) {
            splitQuestion = new HashSet<>(
                    Arrays.asList(question.getTitle().split("\\W"))
            );
        } else if (searchType == SearchType.BODY) {
            splitQuestion = new HashSet<>(
                    Arrays.asList(question.getBody().split("\\W"))
            );
        } else {
            List<TagModel> questionTags = tagRepository.getTags(question.getId(), null);
            splitQuestion = new HashSet<>(
                    questionTags.stream().map(x -> x.getTitle()).collect(Collectors.toList())
            );
        }

        Map<String, Long> frequencies = splitInput.stream()
                .filter(splitQuestion::contains)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        Long frequency = frequencies.values().stream().reduce(0L, Long::sum);
        double percentage = 1.0 * frequency / splitQuestion.size();
        return percentage >= 0.2;
    }

}
