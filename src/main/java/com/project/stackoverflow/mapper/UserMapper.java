package com.project.stackoverflow.mapper;

import com.project.stackoverflow.model.UserModel;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper {
    public static final RowMapper<UserModel> userMapper = (resultSet, i) -> new UserModel(resultSet.getString("id"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("email"),
            resultSet.getString("description"),
            resultSet.getString("profile_picture")
    );

    public static RowMapper<UserModel> getUserMapper() {
        return userMapper;
    }
}
