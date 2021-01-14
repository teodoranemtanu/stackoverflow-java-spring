package com.project.stackoverflow.mapper;

import com.project.stackoverflow.model.AnswerModel;
import org.springframework.jdbc.core.RowMapper;

public class AnswerMapper {
    public static final RowMapper<AnswerModel> answerMapper = (resultSet, i) -> new AnswerModel(resultSet.getString("id"),
            resultSet.getString("body"),
            resultSet.getTimestamp("created_at").toLocalDateTime(),
            resultSet.getString("question_id"),
            resultSet.getString("user_id")
    );

    public static final RowMapper<AnswerModel> answerVoteMapper = (resultSet, i) -> new AnswerModel(resultSet.getString("id"),
            resultSet.getString("body"),
            resultSet.getTimestamp("created_at").toLocalDateTime(),
            resultSet.getString("question_id"),
            resultSet.getString("user_id"),
            resultSet.getInt("vote_count")
    );

    public static RowMapper<AnswerModel> getAnswerMapper() {
        return answerMapper;
    }

    public static RowMapper<AnswerModel> getAnswerVoteMapper() {
        return answerVoteMapper;
    }
}


