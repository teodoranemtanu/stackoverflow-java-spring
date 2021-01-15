package com.project.stackoverflow.repository;

import com.project.stackoverflow.exception.CommunityException;
import com.project.stackoverflow.mapper.UserMapper;
import com.project.stackoverflow.model.UserCommunityModel;
import com.project.stackoverflow.model.UserModel;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserCommunityRepository {
    private final NamedParameterJdbcTemplate template;

    public UserCommunityRepository(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<UserModel> getUsersFromCommunity(String communityId) {
        String sql = "select u.id, u.first_name, u.last_name, u.email, u.description, u.profile_picture " +
                "from users u join users_communities uc on (u.id = uc.user_id) " +
                "where uc.community_id = :community_id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("community_id", communityId);
        return template.query(sql, parameterSource, UserMapper.getUserMapper());
    }

    public boolean joinCommunity(UserCommunityModel userCommunityModel) {
        String insertSql = "insert into users_communities (user_id, community_id, joined_at) " +
                "values (:user_id, :community_id, :joined_at) ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", userCommunityModel.getUserId())
                .addValue("community_id", userCommunityModel.getCommunityId())
                .addValue("joined_at", userCommunityModel.getJoinedAt());

        return template.update(insertSql, parameterSource) == 1;
    }

    public boolean leaveCommunity(String communityId, String userId) {
        String sql = "delete from users_communities where user_id = :user_id and community_id = :community_id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("community_id", communityId);
        return template.update(sql, parameterSource) == 1;
    }
}
