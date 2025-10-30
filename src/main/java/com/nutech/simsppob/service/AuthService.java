package com.nutech.simsppob.service;

import com.nutech.simsppob.model.User;
import com.nutech.simsppob.repository.UserRepository;
import com.nutech.simsppob.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    public boolean register(User user) {
        try {
            User existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null) {
                return false; 
            }
        } catch (EmptyResultDataAccessException e) {
        }

        userRepository.save(user);
        return true; 
    }
    public String login(String email, String password) throws RuntimeException {
        User user;
        try {
            user = userRepository.findByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Username atau password salah");
        }

        if (user != null && user.getPassword().equals(password)) {
            return jwtUtil.generateToken(email); 
        } else {
            throw new RuntimeException("Username atau password salah");
        }
    }
    public boolean validateToken(String token) {
        String email = jwtUtil.extractEmail(token);
        return (email != null && !jwtUtil.isTokenExpired(token));
    }
}
