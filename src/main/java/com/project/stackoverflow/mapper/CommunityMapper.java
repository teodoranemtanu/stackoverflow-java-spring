package com.project.stackoverflow.mapper;

import com.project.stackoverflow.model.CommunityModel;
import org.springframework.jdbc.core.RowMapper;

public class CommunityMapper {
    public static final RowMapper<CommunityModel> communityMapper = (resultSet, i) -> new CommunityModel(resultSet.getString("id"),
            resultSet.getString("title")
    );

    public static RowMapper<CommunityModel> getCommunityMapper() {
        return communityMapper;
    }
}
