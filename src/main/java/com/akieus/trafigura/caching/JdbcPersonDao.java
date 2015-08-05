package com.akieus.trafigura.caching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcPersonDao implements IPersonDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Person findByName(String name) {
        Person person = jdbcTemplate.queryForObject("select id, name from persons where name = ?",
                new RowMapper<Person>() {
                    @Override
                    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Person(resultSet.getInt("id"), resultSet.getString("name"));
                    }
                },
                new String[]{name});
        return person;
    }

    @Override
    public Person findById(int id) {
        Person person = jdbcTemplate.queryForObject("select id, name from persons where id = ?",
                new RowMapper<Person>() {
                    @Override
                    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Person(resultSet.getInt("id"), resultSet.getString("name"));
                    }
                },
                new Object[]{id});
        return person;
    }
}
