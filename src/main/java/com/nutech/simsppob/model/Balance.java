package com.nutech.simsppob.model;

public class Balance {
    private Long userId;
    private Long balance;

    public Long getUserId() { 
        return userId; 
    }

    public void setUserId(Long userId) { 
        this.userId = userId; 
    }

    public Long getBalance() { 
        return balance; 
    }

    public void setBalance(Long balance) { 
        this.balance = balance; 
    }
}
