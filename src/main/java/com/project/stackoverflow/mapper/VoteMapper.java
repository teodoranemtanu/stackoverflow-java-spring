package com.project.stackoverflow.mapper;

import com.project.stackoverflow.model.VoteModel;
import org.springframework.jdbc.core.RowMapper;

public class VoteMapper {
    public static final RowMapper<VoteModel> voteMapper = (resultSet, i) -> new VoteModel(resultSet.getString("id"),
            resultSet.getString("answer_id"),
            resultSet.getString("user_id")
    );

    public static RowMapper<VoteModel> getVoteMapper() {
        return voteMapper;
    }
}
