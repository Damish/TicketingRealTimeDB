package com.damishs.ticketingrealtimedb.Models;

public class Account {

    String customerID;
    String customerName;
    String accountNo;
    String tokenID;
    String availableBalance;

    public Account() {
    }

    public Account(String customerID, String customerName, String accountNo, String tokenID, String availableBalance) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.accountNo = accountNo;
        this.tokenID = tokenID;
        this.availableBalance = availableBalance;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getTokenID() {
        return tokenID;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }
}
