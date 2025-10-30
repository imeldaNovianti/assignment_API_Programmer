package com.nutech.simsppob.repository;

import com.nutech.simsppob.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void save(User user) {
        String sql = "INSERT INTO users(email, password, first_name, last_name, balance, profile_image) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            user.getEmail(), 
            user.getPassword(), 
            user.getFirstName(), 
            user.getLastName(), 
            user.getBalance(),          
            user.getProfileImage()
        );
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password"),
                rs.getInt("balance"),       
                rs.getString("profile_image")
        ), email);
        return DataAccessUtils.singleResult(users);
    }

    public User findById(Long userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password"),
                rs.getInt("balance"),
                rs.getString("profile_image")
        ), userId);
        return DataAccessUtils.singleResult(users);
    }
    public void updateProfile(Long userId, String firstName, String lastName) {
        String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE id = ?";
        jdbcTemplate.update(sql, firstName, lastName, userId);
    }

    public void updateProfileImage(Long userId, String profileImage) {
        String sql = "UPDATE users SET profile_image = ? WHERE id = ?";
        jdbcTemplate.update(sql, profileImage, userId);
    }

    public void updateBalance(Long userId, int newBalance) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        jdbcTemplate.update(sql, newBalance, userId);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password"),
                rs.getInt("balance"),
                rs.getString("profile_image")
        ));
    }
}
