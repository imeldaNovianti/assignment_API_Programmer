package com.nutech.simsppob.repository;

import com.nutech.simsppob.model.ServiceItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServicesRepository {

    private final JdbcTemplate jdbcTemplate;

    public ServicesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ServiceItem> getAllServices() {
        String sql = "SELECT service_code, service_name, service_icon, service_tariff FROM services";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ServiceItem(
                rs.getString("service_code"),
                rs.getString("service_name"),
                rs.getString("service_icon"),
                rs.getInt("service_tariff")
        ));
    }
}
