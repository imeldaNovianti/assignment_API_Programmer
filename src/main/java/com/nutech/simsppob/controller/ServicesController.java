package com.nutech.simsppob.controller;

import com.nutech.simsppob.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ServicesController {

    private final JwtUtil jwtUtil;

    public ServicesController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/services")
    public ResponseEntity<?> getServices(@RequestHeader("Authorization") String token) {
        if (!token.startsWith("Bearer ") || jwtUtil.extractUsername(token.substring(7)) == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", 108,
                    "message", "Token tidak tidak valid atau kadaluwarsa",
                    "data", null
            ));
        }

        List<Map<String, Object>> services = List.of(
                Map.of("service_code", "PAJAK", "service_name", "Pajak PBB", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 40000),
                Map.of("service_code", "PLN", "service_name", "Listrik", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 10000),
                Map.of("service_code", "PDAM", "service_name", "PDAM Berlangganan", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 40000),
                Map.of("service_code", "PULSA", "service_name", "Pulsa", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 40000),
                Map.of("service_code", "PGN", "service_name", "PGN Berlangganan", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 50000),
                Map.of("service_code", "MUSIK", "service_name", "Musik Berlangganan", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 50000),
                Map.of("service_code", "TV", "service_name", "TV Berlangganan", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 50000),
                Map.of("service_code", "PAKET_DATA", "service_name", "Paket data", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 50000),
                Map.of("service_code", "VOUCHER_GAME", "service_name", "Voucher Game", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 100000),
                Map.of("service_code", "VOUCHER_MAKANAN", "service_name", "Voucher Makanan", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 100000),
                Map.of("service_code", "QURBAN", "service_name", "Qurban", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 200000),
                Map.of("service_code", "ZAKAT", "service_name", "Zakat", "service_icon", "https://nutech-integrasi.app/dummy.jpg", "service_tariff", 300000)
        );

        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Sukses",
                "data", services
        ));
    }
}
