package com.nutech.simsppob.controller;

import com.nutech.simsppob.model.Transaction;
import com.nutech.simsppob.service.TransactionService;
import com.nutech.simsppob.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final JwtUtil jwtUtil;

    public TransactionController(TransactionService transactionService, JwtUtil jwtUtil) {
        this.transactionService = transactionService;
        this.jwtUtil = jwtUtil;
    }

    private String getEmailFromBearer(String token) {
        if (token == null || !token.startsWith("Bearer ")) return null;
        return jwtUtil.extractUsername(token.substring(7));
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestHeader("Authorization") String token) {
        String email = getEmailFromBearer(token);
        if (email == null)
            return ResponseEntity.status(401).body(Map.of(
                    "status", 108,
                    "message", "Token tidak tidak valid atau kadaluwarsa",
                    "data", null
            ));

        int balance = transactionService.getBalance(email);
        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Get Balance Berhasil",
                "data", Map.of("balance", balance)
        ));
    }

    @PostMapping("/topup")
    public ResponseEntity<?> topUp(@RequestHeader("Authorization") String token,
                                   @RequestBody Map<String, Object> body) {
        String email = getEmailFromBearer(token);
        if (email == null)
            return ResponseEntity.status(401).body(Map.of(
                    "status", 108,
                    "message", "Token tidak tidak valid atau kadaluwarsa",
                    "data", null
            ));

        Integer amount = (Integer) body.get("top_up_amount");
        if (amount == null || amount <= 0)
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 102,
                    "message", "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0",
                    "data", null
            ));

        int newBalance = transactionService.topUp(email, amount);
        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Top Up Balance berhasil",
                "data", Map.of("balance", newBalance)
        ));
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> doTransaction(@RequestHeader("Authorization") String token,
                                           @RequestBody Map<String, Object> body) {
        String email = getEmailFromBearer(token);
        if (email == null)
            return ResponseEntity.status(401).body(Map.of(
                    "status", 108,
                    "message", "Token tidak tidak valid atau kadaluwarsa",
                    "data", null
            ));

        String serviceCode = (String) body.get("service_code");
        if (serviceCode == null || serviceCode.isBlank())
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 102,
                    "message", "Service atau Layanan tidak ditemukan",
                    "data", null
            ));

        Transaction trx = transactionService.performTransaction(email, serviceCode);
        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Transaksi berhasil",
                "data", Map.of(
                        "invoice_number", trx.getInvoice_number(),
                        "service_code", trx.getService_code(),
                        "service_name", trx.getService_name(),
                        "transaction_type", trx.getTransaction_type(),
                        "total_amount", trx.getTotal_amount(),
                        "created_on", trx.getCreated_on().format(DateTimeFormatter.ISO_DATE_TIME)
                )
        ));
    }

    @GetMapping("/transaction/history")
    public ResponseEntity<?> getHistory(@RequestHeader("Authorization") String token,
                                        @RequestParam(defaultValue = "0") int offset,
                                        @RequestParam(defaultValue = "0") int limit) {
        String email = getEmailFromBearer(token);
        if (email == null)
            return ResponseEntity.status(401).body(Map.of(
                    "status", 108,
                    "message", "Token tidak tidak valid atau kadaluwarsa",
                    "data", null
            ));

        List<Transaction> trxList = transactionService.getTransactionHistory(
                email, offset, limit == 0 ? Integer.MAX_VALUE : limit);

        Map<String, Object> data = Map.of(
                "offset", offset,
                "limit", limit,
                "records", trxList.stream().map(trx -> Map.of(
                        "invoice_number", trx.getInvoice_number(),
                        "transaction_type", trx.getTransaction_type(),
                        "description", trx.getDescription(),
                        "total_amount", trx.getTotal_amount(),
                        "created_on", trx.getCreated_on().format(DateTimeFormatter.ISO_DATE_TIME)
                )).collect(Collectors.toList())
        );

        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Get History Berhasil",
                "data", data
        ));
    }
}
