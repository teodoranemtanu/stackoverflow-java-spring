package com.project.stackoverflow.repository;

import com.project.stackoverflow.exception.UserException;
import com.project.stackoverflow.model.UserModel;
import com.project.stackoverflow.util.UserMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    public void saveUser(UserModel userModel) {
        if (getUsers(null).stream().anyMatch(user -> user.getId().equals(userModel.getDescription()))) {
            throw new UserException();
        }

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
            template.update(insertSql, parameterSource);
        }
    }

    public void removeUser(String id) {
        String sql = "delete from users where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource().addValue("id", id);
        if (template.update(sql, parameterSource) != 1) {
            throw new UserException();
        }
    }

    public UserModel getUserById(String id) {
        String sql = "select id, first_name, last_name, email, description, profile_picture from users " +
                "where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        List<UserModel> result = template.query(sql, parameterSource, UserMapper.getUserMapper());
        try {
            return result.get(0);
        } catch(Exception exception) {
         throw new UserException();
        }
    }
}
