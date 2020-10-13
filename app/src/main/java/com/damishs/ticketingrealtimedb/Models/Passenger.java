package com.damishs.ticketingrealtimedb.Models;

public class Passenger {

    String id;
    String nic;
    String name;
    String tokenID;
    String accountNo;
    String username;
    String password;


    public Passenger() {

    }

    public Passenger(String id, String nic, String name, String tokenID, String accountNo, String username, String password) {
        this.id = id;
        this.nic = nic;
        this.name = name;
        this.tokenID = tokenID;
        this.accountNo = accountNo;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getNic() {
        return nic;
    }

    public String getName() {
        return name;
    }

    public String getTokenID() {
        return tokenID;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
