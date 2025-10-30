package com.nutech.simsppob.repository;

import com.nutech.simsppob.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveTransaction(Long userId, Transaction trx) {
        String sql = "INSERT INTO transactions (user_id, invoice_number, service_code, service_name, transaction_type, description, total_amount, created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                userId,
                trx.getInvoice_number(),
                trx.getService_code(),
                trx.getService_name(),
                trx.getTransaction_type(),
                trx.getDescription(),
                trx.getTotal_amount(),
                Timestamp.valueOf(trx.getCreated_on())
        );
    }

    public List<Transaction> getTransactionHistory(Long userId, int offset, int limit) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY created_on DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Transaction(
                        rs.getString("invoice_number"),
                        rs.getString("transaction_type"),
                        rs.getString("description"),
                        rs.getInt("total_amount"),
                        rs.getTimestamp("created_on").toLocalDateTime(),
                        rs.getString("service_code"),
                        rs.getString("service_name")
                ),
                userId, limit, offset
        );
    }
}
