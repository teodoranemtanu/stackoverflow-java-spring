package com.project.stackoverflow.repository;

import com.project.stackoverflow.exception.CommunityException;
import com.project.stackoverflow.model.CommunityModel;
import com.project.stackoverflow.mapper.CommunityMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CommunityRepository {
    private final NamedParameterJdbcTemplate template;

    public CommunityRepository(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<CommunityModel> getCommunities() {
        String sql = "select id, title from communities";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        return template.query(sql, parameterSource, CommunityMapper.getCommunityMapper());
    }

    public CommunityModel getCommunityById(String id) {
        String sql = "select id, title from communities " +
                "where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<CommunityModel> result = template.query(sql, parameterSource, CommunityMapper.getCommunityMapper());
        try {
            return result.get(0);
        } catch (Exception exception) {
            throw new CommunityException();
        }
    }

    public void saveCommunity(CommunityModel communityModel) {
        if (getCommunities()
                .stream().anyMatch(x -> x.getId().equals(communityModel.getId()))) {
            throw new CommunityException();
        }

        String updateSql = "update questions set " +
                "title = :title, " +
                "body = :body, " +
                "active = :active, " +
                "created_at = :created_at, " +
                "user_id = :user_id, " +
                "community_id = :community_id " +
                "where id = :id";
        String insertSql = "insert into questions (id, title) " +
                "values (:id, :title) ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", communityModel.getId())
                .addValue("title", communityModel.getTitle());

        if (template.update(updateSql, parameterSource) != 1) {
            template.update(insertSql, parameterSource);
        }
    }

    public void removeCommunity(String id) {
        String sql = "delete from communities where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        if (template.update(sql, parameterSource) != 1) {
            throw new CommunityException();
        }
    }


}
