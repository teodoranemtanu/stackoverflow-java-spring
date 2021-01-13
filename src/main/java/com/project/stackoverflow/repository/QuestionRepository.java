package com.project.stackoverflow.repository;

import com.project.stackoverflow.exception.QuestionException;
import com.project.stackoverflow.model.QuestionModel;
import com.project.stackoverflow.mapper.QuestionMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class QuestionRepository {
    private final NamedParameterJdbcTemplate template;

    public QuestionRepository(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<QuestionModel> getQuestions(String userId, String communityId) {
        String sql = "select id, title, body, created_at, active, user_id, community_id from questions";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        if (userId != null) {
            sql += " where user_id = :user_id";
            parameterSource.addValue("user_id", userId);

            if (communityId != null) {
                sql += " and community_id = :community_id";
                parameterSource.addValue("community_id", communityId);
            }
        } else if (communityId != null) {
            sql += " where community_id = :community_id";
            parameterSource.addValue("community_id", communityId);
        }

        sql += " order by created_at desc";

        return template.query(sql, parameterSource, QuestionMapper.getQuestionMapper());
    }

    public QuestionModel getQuestionById(String id) {
        String sql = "select id, title, body, created_at, active, user_id, community_id from questions " +
                "where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<QuestionModel> result = template.query(sql, parameterSource, QuestionMapper.getQuestionMapper());
        try {
            return result.get(0);
        } catch (Exception exception) {
            throw new QuestionException();
        }
    }

    public void saveQuestion(QuestionModel questionModel) {
        if (getQuestions(null, null)
                .stream().anyMatch(x -> x.getId().equals(questionModel.getId()))) {
            throw new QuestionException();
        }

        String updateSql = "update questions set " +
                "title = :title, " +
                "body = :body, " +
                "active = :active, " +
                "created_at = :created_at, " +
                "user_id = :user_id, " +
                "community_id = :community_id " +
                "where id = :id";
        String insertSql = "insert into questions (id, title, body, active, created_at, user_id, community_id) " +
                "values (:id, :title, :body, :active, :created_at, :user_id, :community_id) ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", questionModel.getId())
                .addValue("title", questionModel.getTitle())
                .addValue("body", questionModel.getBody())
                .addValue("active", questionModel.isActive())
                .addValue("created_at", questionModel.getCreatedAt())
                .addValue("user_id", questionModel.getUserId())
                .addValue("community_id", questionModel.getCommunityId());

        if (template.update(updateSql, parameterSource) != 1) {
            template.update(insertSql, parameterSource);
        }
    }

    public void removeQuestion(String id) {
        String sql = "delete from questions where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        if (template.update(sql, parameterSource) != 1) {
            throw new QuestionException();
        }
    }

}
