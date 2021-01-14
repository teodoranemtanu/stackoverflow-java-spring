package com.project.stackoverflow.repository;

import com.project.stackoverflow.exception.VoteException;
import com.project.stackoverflow.model.VoteModel;
import com.project.stackoverflow.mapper.VoteMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class VoteRepository {
    private final NamedParameterJdbcTemplate template;

    public VoteRepository(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<VoteModel> getVotes(String answerId, String userId) {
        String sql = "select id, answer_id, user_id from votes";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        if (answerId != null) {
            sql += " where answer_id = :answer_id";
            parameterSource.addValue("answer_id", answerId);

            if (userId != null) {
                sql += " and user_id = :user_id";
                parameterSource.addValue("user_id", userId);
            }
        } else if (userId != null) {
            sql += " where user_id = :user_id";
            parameterSource.addValue("user_id", userId);
        }
        return template.query(sql, parameterSource, VoteMapper.getVoteMapper());
    }

    public void saveVote(VoteModel voteModel) {
        if (getVotes(voteModel.getAnswerId(), voteModel.getUserId()).stream()
                .filter(x -> !x.getAnswerId().equals(voteModel.getAnswerId()))
                .anyMatch(x -> x.getUserId().equals(voteModel.getUserId()))) {
            throw new VoteException();
        }

        String sql = "insert into votes (id, answer_id, user_id) " +
                "values (:id, :answer_id, :user_id)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", voteModel.getId())
                .addValue("answer_id", voteModel.getAnswerId())
                .addValue("user_id", voteModel.getUserId());

        template.update(sql, parameterSource);
    }

    public void removeVote(String id) {
        String sql = "delete from votes where id =:id";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
        if(template.update(sql, parameters) != 1){
            throw new VoteException();
        }
    }

    public void removeVote(String answerId, String userId) {
        String sql = "delete from votes where user_id = :user_id and answer_id = :answer_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("answer_id", answerId)
                .addValue("user_id", userId);
        if(template.update(sql, parameters) != 1){
            throw new VoteException();
        }
    }
}
