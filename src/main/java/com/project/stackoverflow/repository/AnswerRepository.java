package com.project.stackoverflow.repository;

import com.project.stackoverflow.exception.AnswerException;
import com.project.stackoverflow.model.AnswerModel;
import com.project.stackoverflow.mapper.AnswerMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class AnswerRepository {
    private final NamedParameterJdbcTemplate template;

    public AnswerRepository(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<AnswerModel> getAnswers(String questionId, String userId) {
        String sql = "select id, body, created_at, question_id, user_id from answers ";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        if (questionId != null) {
            sql += " where question_id = :question_id";
            parameterSource.addValue("question_id", questionId);

            if (userId != null) {
                sql += " and user_id = :user_id";
                parameterSource.addValue("user_id", userId);
            }
        } else if (userId != null) {
            sql += " where user_id = :user_id";
            parameterSource.addValue("user_id", userId);
        }
        return template.query(sql, parameterSource, AnswerMapper.getAnswerMapper());
    }

    public Optional<AnswerModel> getAnswerById(String id) {
        String sql = "select id, body, created_at, question_id, user_id  from answers " +
                "where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<AnswerModel> result = template.query(sql, parameterSource, AnswerMapper.getAnswerMapper());
        return result.stream().findFirst();
    }

    public boolean saveAnswer(AnswerModel answerModel) {
        String updateSql = "update answers set " +
                "body = :body, " +
                "created_at = :created_at, " +
                "question_id = :question_id, " +
                "user_id = :user_id " +
                "where id = :id";
        String insertSql = "insert into answers (id, body, created_at, question_id, user_id) " +
                "values (:id, :body, :created_at, :question_id, :user_id) ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", answerModel.getId())
                .addValue("body", answerModel.getBody())
                .addValue("created_at", answerModel.getCreatedAt())
                .addValue("question_id", answerModel.getQuestionId())
                .addValue("user_id", answerModel.getUserId());

        if (template.update(updateSql, parameterSource) != 1) {
            return (template.update(insertSql, parameterSource) == 1);
        }
        return true;
    }

    public boolean removeAnswer(String id) {
        String sql = "delete from answers where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return template.update(sql, parameterSource) == 1;
    }

}
