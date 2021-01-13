package com.project.stackoverflow.mapper;

import com.project.stackoverflow.model.QuestionModel;
import org.springframework.jdbc.core.RowMapper;

public class QuestionMapper {
    public static final RowMapper<QuestionModel> questionMapper = (resultSet, i) -> new QuestionModel(resultSet.getString("id"),
            resultSet.getString("title"),
            resultSet.getString("body"),
            resultSet.getTimestamp("created_at").toLocalDateTime(),
            resultSet.getBoolean("active"),
            resultSet.getString("user_id"),
            resultSet.getString("community_id")
    );

    public static RowMapper<QuestionModel> getQuestionMapper() {
        return questionMapper;
    }
}
