package com.damishs.ticketingrealtimedb.Models;

public class Token {

    String tokenID;
    String customerID;
    String departureTime;
    String departureVenue;
    String arrivalTime;
    String arrivalVenue;
    String fareAmount;


    public Token(){
    };

    public Token(String tokenID, String customerID, String departureTime, String departureVenue, String arrivalTime, String arrivalVenue, String fareAmount) {
        this.tokenID = tokenID;
        this.customerID = customerID;
        this.departureTime = departureTime;
        this.departureVenue = departureVenue;
        this.arrivalTime = arrivalTime;
        this.arrivalVenue = arrivalVenue;
        this.fareAmount = fareAmount;
    }


    public String getTokenID() {
        return tokenID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDepartureVenue() {
        return departureVenue;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getArrivalVenue() {
        return arrivalVenue;
    }

    public String getFareAmount() {
        return fareAmount;
    }

}
