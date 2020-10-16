package com.damishs.ticketingrealtimedb.ui.Lists;

public class MyTrip {

    String departureTime;
    String departureVenue;
    String arrivalTime;
    String arrivalVenue;
    String fareAmount;


    public MyTrip() {
    }

    public MyTrip(String departureTime, String departureVenue, String arrivalTime, String arrivalVenue, String fareAmount) {
        this.departureTime = departureTime;
        this.departureVenue = departureVenue;
        this.arrivalTime = arrivalTime;
        this.arrivalVenue = arrivalVenue;
        this.fareAmount = fareAmount;
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
