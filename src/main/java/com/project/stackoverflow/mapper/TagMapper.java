package com.project.stackoverflow.mapper;

import com.project.stackoverflow.model.TagModel;
import com.project.stackoverflow.model.UserModel;
import org.springframework.jdbc.core.RowMapper;

public class TagMapper {
    public static final RowMapper<TagModel> tagMapper = (resultSet, i) -> new TagModel(resultSet.getString("id"),
            resultSet.getString("title"),
            resultSet.getString("question_id"),
            resultSet.getString("community_id")
    );

    public static RowMapper<TagModel> getTagMapper() {
        return tagMapper;
    }
}
