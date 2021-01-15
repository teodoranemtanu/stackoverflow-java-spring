package com.project.stackoverflow.repository;

import com.project.stackoverflow.exception.QuestionException;
import com.project.stackoverflow.exception.TagException;
import com.project.stackoverflow.mapper.TagMapper;
import com.project.stackoverflow.model.TagModel;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TagRepository {
    private final NamedParameterJdbcTemplate template;

    public TagRepository(final DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<TagModel> getTags(String questionId, String communityId) {
        String sql = "select id, title, question_id, community_id from tags";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        if (questionId != null) {
            sql += " where question_id = :question_id";
            parameterSource.addValue("question_id", questionId);

            if (communityId != null) {
                sql += " and community_id = :community_id";
                parameterSource.addValue("community_id", communityId);
            }
        } else if (communityId != null) {
            sql += " where community_id = :community_id";
            parameterSource.addValue("community_id", communityId);
        }
        return template.query(sql, parameterSource, TagMapper.getTagMapper());
    }

    public boolean saveTag(TagModel tagModel) {
        String updateSql = "update tags set " +
                "title = :title, " +
                "question_id = :question_id " +
                "where id = :id";
        String insertSql = "insert into tags (id, title, question_id) " +
                "values (:id, :title, :question_id) ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", tagModel.getId())
                .addValue("title", tagModel.getTitle())
                .addValue("question_id", tagModel.getQuestionId());

        if (template.update(updateSql, parameterSource) != 1) {
            return (template.update(insertSql, parameterSource) == 1);
        }
        return true;
    }

    public boolean removeTag(String id) {
        String sql = "delete from tags where id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return template.update(sql, parameters) == 1;
    }
}
