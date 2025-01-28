package com.wsis.atinsos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test")
    public String test() {
        return "Application is running!";
    }

    @GetMapping("/dbtest")
    public String testDb() {
        try {
            // Wykonaj rzeczywiste zapytanie, które wymusi komunikację z bazą
            String result = jdbcTemplate.queryForObject(
                    "SELECT CURRENT_TIMESTAMP FROM (VALUES(0))",
                    String.class
            );
            return "Database is active! Current timestamp: " + result;
        } catch (Exception e) {
            return "Database connection error: " + e.getMessage();
        }
    }
}