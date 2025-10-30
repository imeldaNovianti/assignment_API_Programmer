package com.nutech.simsppob.controller;

import com.nutech.simsppob.model.User;
import com.nutech.simsppob.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody(required = false) User user) {
        Map<String, Object> res = new HashMap<>();

        if (user == null) {
            res.put("status", 102);
            res.put("message", "Request body tidak boleh kosong");
            res.put("data", null);
            return ResponseEntity.badRequest().body(res);
        }

        if (user.getEmail() == null || !user.getEmail().matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$")) {
            res.put("status", 102);
            res.put("message", "Parameter email tidak sesuai format");
            res.put("data", null);
            return ResponseEntity.badRequest().body(res);
        }

        if (user.getPassword() == null || user.getPassword().length() < 8) {
            res.put("status", 102);
            res.put("message", "Password minimal 8 karakter");
            res.put("data", null);
            return ResponseEntity.badRequest().body(res);
        }

        try {
            boolean registered = authService.register(user); // return boolean
            if (!registered) {
                res.put("status", 104);
                res.put("message", "Email sudah terdaftar");
                res.put("data", null);
                return ResponseEntity.badRequest().body(res);
            }

            res.put("status", 0);
            res.put("message", "Registrasi berhasil silahkan login");
            res.put("data", null);
            return ResponseEntity.ok(res);

        } catch (Exception e) {
            res.put("status", 500);
            res.put("message", "Terjadi kesalahan server");
            res.put("data", null);
            return ResponseEntity.status(500).body(res);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = false) Map<String, String> body) {
        Map<String, Object> res = new HashMap<>();

        if (body == null || !body.containsKey("email") || !body.containsKey("password")) {
            res.put("status", 102);
            res.put("message", "Email dan password wajib diisi");
            res.put("data", null);
            return ResponseEntity.badRequest().body(res);
        }

        String email = body.get("email");
        String password = body.get("password");

        try {
            String token = authService.login(email, password);
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);

            res.put("status", 0);
            res.put("message", "Login Sukses");
            res.put("data", data);
            return ResponseEntity.ok(res);

        } catch (Exception e) {
            res.put("status", 103);
            res.put("message", "Username atau password salah");
            res.put("data", null);
            return ResponseEntity.status(401).body(res);
        }
    }
}
