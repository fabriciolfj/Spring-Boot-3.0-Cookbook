package com.packt.footballobs.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DataService {

    private final JdbcTemplate jdbcTemplate;

    public DataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getPlayerStats(final String playerName) {
        final Random random = new Random();
        jdbcTemplate.execute("SELECT pg_sleep(" + random.nextDouble(1.0) + ")");
        return "Player " + playerName + " has " + random.nextInt(100) + " points";
    }
}
