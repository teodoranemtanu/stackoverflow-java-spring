package com.project.stackoverflow.repository;

import com.project.stackoverflow.exception.UserException;
import com.project.stackoverflow.model.UserModel;
import com.project.stackoverflow.mapper.UserMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate template;

    public UserRepository(final DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<UserModel> getUsers(String communityId) {
        String sql = "select id, first_name, last_name, email, description, profile_picture from users";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        if(communityId != null) {
            sql += " join users_communities on (users.id = users_communities.user_id) where users_communities.community_id = :community_id";
            parameterSource.addValue("community_id", communityId);
        }
        List<UserModel> result = template.query(sql, parameterSource, UserMapper.getUserMapper());
        return result;
    }

    public boolean saveUser(UserModel userModel) {
        String updateSql = "update users set " +
                "first_name = :first_name, " +
                "last_name = :last_name, " +
                "email = :email, " +
                "description = :description," +
                "profile_picture = :profile_picture " +
                "where id = :id ";
        String insertSql = "insert into users (id, first_name, last_name, email, description, profile_picture) " +
                "values (:id, :first_name, :last_name, :email, :description, :profile_picture) ";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", userModel.getId())
                .addValue("first_name", userModel.getFirstName())
                .addValue("last_name", userModel.getLastName())
                .addValue("email", userModel.getEmail())
                .addValue("description", userModel.getDescription())
                .addValue("profile_picture", userModel.getProfilePicture());

        if (template.update(updateSql, parameterSource) != 1) {
            return (template.update(insertSql, parameterSource) == 1);
        }
        return true;
    }

    public boolean removeUser(String id) {
        String sql = "delete from users where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        return template.update(sql, parameterSource) == 1;
    }

    public Optional<UserModel> getUserById(String id) {
        String sql = "select id, first_name, last_name, email, description, profile_picture from users " +
                "where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<UserModel> result = template.query(sql, parameterSource, UserMapper.getUserMapper());
        return result.stream().findFirst();
    }
}
