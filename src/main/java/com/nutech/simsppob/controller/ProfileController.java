package com.nutech.simsppob.controller;

import com.nutech.simsppob.model.User;
import com.nutech.simsppob.service.ProfileService;
import com.nutech.simsppob.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtUtil jwtUtil;

    private String getEmailFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return jwtUtil.extractUsername(token.substring(7));
    }

    @GetMapping
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String email = getEmailFromToken(token);
        if (email == null) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 108);
            res.put("message", "Token tidak valid atau kadaluwarsa");
            res.put("data", null);
            return ResponseEntity.status(401).body(res);
        }

        User user = profileService.getProfile(email);
        if (user == null) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 102);
            res.put("message", "User tidak ditemukan");
            res.put("data", null);
            return ResponseEntity.status(404).body(res);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("status", 0);
        res.put("message", "Sukses");
        res.put("data", user);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token,
                                           @RequestBody Map<String, String> body) {
        String email = getEmailFromToken(token);
        if (email == null) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 108);
            res.put("message", "Token tidak valid atau kadaluwarsa");
            res.put("data", null);
            return ResponseEntity.status(401).body(res);
        }

        String firstName = body.get("first_name");
        String lastName = body.get("last_name");

        if (firstName == null || lastName == null) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 102);
            res.put("message", "first_name dan last_name wajib diisi");
            res.put("data", null);
            return ResponseEntity.badRequest().body(res);
        }

        User user = profileService.updateProfile(email, firstName, lastName);
        Map<String, Object> res = new HashMap<>();
        res.put("status", 0);
        res.put("message", "Update Profile berhasil");
        res.put("data", user);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/image")
    public ResponseEntity<?> updateProfileImage(@RequestHeader("Authorization") String token,
                                                @RequestParam("file") MultipartFile file) {
        String email = getEmailFromToken(token);
        if (email == null) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 108);
            res.put("message", "Token tidak valid atau kadaluwarsa");
            res.put("data", null);
            return ResponseEntity.status(401).body(res);
        }

        if (file == null || file.isEmpty()) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 102);
            res.put("message", "file wajib diisi");
            res.put("data", null);
            return ResponseEntity.badRequest().body(res);
        }

        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 102);
            res.put("message", "Format Image tidak sesuai");
            res.put("data", null);
            return ResponseEntity.badRequest().body(res);
        }

        String imageUrl = profileService.storeProfileImage(file);
        User user = profileService.updateProfileImage(email, imageUrl);
        Map<String, Object> res = new HashMap<>();
        res.put("status", 0);
        res.put("message", "Update Profile Image berhasil");
        res.put("data", user);
        return ResponseEntity.ok(res);
    }
}
