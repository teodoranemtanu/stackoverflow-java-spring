package com.project.stackoverflow.repository;

import com.project.stackoverflow.exception.CommunityException;
import com.project.stackoverflow.model.CommunityModel;
import com.project.stackoverflow.mapper.CommunityMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

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

    public Optional<CommunityModel> getCommunityById(String id) {
        String sql = "select id, title from communities " +
                "where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<CommunityModel> result = template.query(sql, parameterSource, CommunityMapper.getCommunityMapper());
        return result.stream().findFirst();
    }

    public boolean saveCommunity(CommunityModel communityModel) {
        String updateSql = "update communities set title = :title where id = :id";
        String insertSql = "insert into communities (id, title) values (:id, :title) ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", communityModel.getId())
                .addValue("title", communityModel.getTitle());

        if (template.update(updateSql, parameterSource) != 1) {
            return (template.update(insertSql, parameterSource) == 1);
        }
        return true;
    }

    public boolean removeCommunity(String id) {
        String sql = "delete from communities where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return template.update(sql, parameterSource) == 1;
    }
}
