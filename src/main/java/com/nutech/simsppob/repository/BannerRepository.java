package com.nutech.simsppob.repository;

import com.nutech.simsppob.model.Banner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BannerRepository {

    private final JdbcTemplate jdbcTemplate;

    public BannerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Banner> getAllBanners() {
        String sql = "SELECT banner_name, banner_image, description FROM banners";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Banner(
                rs.getString("banner_name"),
                rs.getString("banner_image"),
                rs.getString("description")
        ));
    }
}
