package com.damishs.ticketingrealtimedb.Models;

public class QRCode {

    String tokenID;
    String QRCodeID;
    String departureTime;
    String departureVenue;
    String arrivalTime;
    String arrivalVenue;
    String fareAmount;

    public QRCode(){
    };

    public QRCode(String tokenID, String QRCodeID, String departureTime, String departureVenue, String arrivalTime, String arrivalVenue, String fareAmount) {
        this.tokenID = tokenID;
        this.QRCodeID = QRCodeID;
        this.departureTime = departureTime;
        this.departureVenue = departureVenue;
        this.arrivalTime = arrivalTime;
        this.arrivalVenue = arrivalVenue;
        this.fareAmount = fareAmount;
    }


    public String getTokenID() {
        return tokenID;
    }

    public String getQRCodeID() {
        return QRCodeID;
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
