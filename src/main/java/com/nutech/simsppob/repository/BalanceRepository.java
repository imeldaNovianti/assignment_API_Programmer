package com.nutech.simsppob.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BalanceRepository {
    private final JdbcTemplate jdbcTemplate;

    public BalanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getBalance(String email) {
        String sql = "SELECT balance FROM balances b JOIN users u ON b.user_id = u.id WHERE u.email = ?";
        Integer balance = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return balance != null ? balance : 0;
    }

    public void updateBalance(String email, int newBalance) {
        String sql = "UPDATE balances b JOIN users u ON b.user_id = u.id SET b.balance = ? WHERE u.email = ?";
        jdbcTemplate.update(sql, newBalance, email);
    }

    public int getBalance(Long userId) {
        String sql = "SELECT balance FROM balances WHERE user_id = ?";
        Integer balance = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return balance != null ? balance : 0;
    }

    public void updateBalance(Long userId, int newBalance) {
        String sql = "UPDATE balances SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, newBalance, userId);
    }
}
