package com.damishs.ticketingrealtimedb.Models;

public class Token {

    String tokenID;
    String customerID;

    public Token(){
    };

    public Token(String tokenID, String customerID) {
        this.tokenID = tokenID;
        this.customerID = customerID;
    }

    public String getTokenID() {
        return tokenID;
    }

    public String getCustomerID() {
        return customerID;
    }

}
