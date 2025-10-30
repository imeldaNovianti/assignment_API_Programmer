package com.nutech.simsppob.service;

import com.nutech.simsppob.model.User;
import com.nutech.simsppob.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    private final String uploadDir = "uploads/profile_images/";
    public User getProfile(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateProfile(String email, String firstName, String lastName) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.updateProfile(user.getId(), firstName, lastName);
            user.setFirstName(firstName);
            user.setLastName(lastName);
        }
        return user;
    }

    public User updateProfileImage(String email, String profileImage) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.updateProfileImage(user.getId(), profileImage);
            user.setProfileImage(profileImage);
        }
        return user;
    }

    public String storeProfileImage(MultipartFile file) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filepath = Paths.get(uploadDir, filename);
            Files.write(filepath, file.getBytes());

            return filepath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file: " + e.getMessage());
        }
    }
}
