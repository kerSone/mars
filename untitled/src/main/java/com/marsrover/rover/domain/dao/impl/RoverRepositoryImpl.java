package com.marsrover.rover.domain.dao.impl;

import com.marsrover.rover.domain.dao.RoverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class RoverRepositoryImpl implements RoverRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int addResult(String input, String output) {
        int resultId = isThereAnyResult();

        return jdbcTemplate.update(
                "INSERT INTO RESPONSE VALUES (?, ?, ?)"
                , resultId
                , input
                , output);
    }

    @Override
    public String getOutput(String input) {
        if(isThereResult(input) > 0 ){
            return jdbcTemplate.queryForObject(
                    "SELECT OUTPUT FROM RESPONSE WHERE INPUT = ?"
                    , new Object[]{input}
                    , String.class);
        }
        return null;
    }

    private int isThereAnyResult(){
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM RESPONSE", Integer.class);
    }

    private int isThereResult(String input){
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM RESPONSE WHERE INPUT = ?"
                , new Object[]{input}
                , Integer.class);
    }
}
