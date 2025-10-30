package com.nutech.simsppob.service;

import com.nutech.simsppob.model.Transaction;
import com.nutech.simsppob.model.User;
import com.nutech.simsppob.repository.TransactionRepository;
import com.nutech.simsppob.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public int getBalance(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? user.getBalance() : 0;
    }

    public int topUp(String email, int amount) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException("User tidak ditemukan");
        int newBalance = user.getBalance() + amount;
        user.setBalance(newBalance);
        userRepository.updateBalance(user.getId(), newBalance);
        Transaction trx = new Transaction(
                "INV" + UUID.randomUUID().toString(),
                "TOPUP",
                "Top Up balance",
                amount,
                LocalDateTime.now(),
                null,
                null
        );
        transactionRepository.saveTransaction(user.getId(), trx);
        return newBalance;
    }

    public Transaction performTransaction(String email, String serviceCode) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException("User tidak ditemukan");

        int amount = switch (serviceCode) {
            case "PLN" -> 10000;
            case "PULSA" -> 40000;
            case "VOUCHER_GAME" -> 100000;
            default -> throw new RuntimeException("Service atau Layanan tidak ditemukan");
        };

        if (user.getBalance() < amount) throw new RuntimeException("Saldo tidak mencukupi");
        int newBalance = user.getBalance() - amount;
        user.setBalance(newBalance);
        userRepository.updateBalance(user.getId(), newBalance);

        Transaction trx = new Transaction(
                "INV" + UUID.randomUUID().toString(),
                "PAYMENT",
                serviceCode + " Payment",
                amount,
                LocalDateTime.now(),
                serviceCode,
                serviceCode
        );

        transactionRepository.saveTransaction(user.getId(), trx);
        return trx;
    }

    public java.util.List<Transaction> getTransactionHistory(String email, int offset, int limit) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException("User tidak ditemukan");
        return transactionRepository.getTransactionHistory(user.getId(), offset, limit);
    }
}
