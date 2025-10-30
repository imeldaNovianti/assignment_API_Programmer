package com.nutech.simsppob.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BannerController {

    @GetMapping("/banner")
    public ResponseEntity<?> getBanners() {
        List<Map<String, Object>> banners = List.of(
                Map.of(
                        "banner_name", "Banner 1",
                        "banner_image", "https://nutech-integrasi.app/dummy.jpg",
                        "description", "Lerem Ipsum Dolor sit amet"
                ),
                Map.of(
                        "banner_name", "Banner 2",
                        "banner_image", "https://nutech-integrasi.app/dummy.jpg",
                        "description", "Lerem Ipsum Dolor sit amet"
                ),
                Map.of(
                        "banner_name", "Banner 3",
                        "banner_image", "https://nutech-integrasi.app/dummy.jpg",
                        "description", "Lerem Ipsum Dolor sit amet"
                ),
                Map.of(
                        "banner_name", "Banner 4",
                        "banner_image", "https://nutech-integrasi.app/dummy.jpg",
                        "description", "Lerem Ipsum Dolor sit amet"
                ),
                Map.of(
                        "banner_name", "Banner 5",
                        "banner_image", "https://nutech-integrasi.app/dummy.jpg",
                        "description", "Lerem Ipsum Dolor sit amet"
                ),
                Map.of(
                        "banner_name", "Banner 6",
                        "banner_image", "https://nutech-integrasi.app/dummy.jpg",
                        "description", "Lerem Ipsum Dolor sit amet"
                )
        );

        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Sukses",
                "data", banners
        ));
    }
}
