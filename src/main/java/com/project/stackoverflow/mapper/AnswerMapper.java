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

    public static RowMapper<AnswerModel> getAnswerMapper() {
        return answerMapper;
    }
}


