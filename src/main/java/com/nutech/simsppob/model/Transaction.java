package com.nutech.simsppob.model;

import java.time.LocalDateTime;

public class Transaction {
    private String invoice_number;
    private String transaction_type;
    private String description;
    private int total_amount;
    private LocalDateTime created_on;
    private String service_code;
    private String service_name;

    public Transaction(String invoice_number, String transaction_type, String description, int total_amount, LocalDateTime created_on, String service_code, String service_name) {
        this.invoice_number = invoice_number;
        this.transaction_type = transaction_type;
        this.description = description;
        this.total_amount = total_amount;
        this.created_on = created_on;
        this.service_code = service_code;
        this.service_name = service_name;
    }

    public String getInvoice_number() { return invoice_number; }
    public String getTransaction_type() { return transaction_type; }
    public String getDescription() { return description; }
    public int getTotal_amount() { return total_amount; }
    public LocalDateTime getCreated_on() { return created_on; }
    public String getService_code() { return service_code; }
    public String getService_name() { return service_name; }
}
