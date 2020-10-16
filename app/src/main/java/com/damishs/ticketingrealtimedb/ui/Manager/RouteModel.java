package com.damishs.ticketingrealtimedb.ui.Manager;

public class RouteModel {

    String routeID;
    String bus;
    String day;
    String startpoint;
    String endpoint;
    String routeprice;
    String noofpassengers;
    String Invalidpassenger;
    String TotalFare;
    String timeperiod;
    String validpassenger;

    public RouteModel() {
    }

    public RouteModel(String routeID, String bus, String day, String startpoint, String endpoint, String routeprice, String noofpassengers, String invalidpassenger, String totalFare, String timeperiod, String validpassenger) {
        this.routeID = routeID;
        this.bus = bus;
        this.day = day;
        this.startpoint = startpoint;
        this.endpoint = endpoint;
        this.routeprice = routeprice;
        this.noofpassengers = noofpassengers;
        this.Invalidpassenger = invalidpassenger;
        this.TotalFare = totalFare;
        this.timeperiod = timeperiod;
        this.validpassenger = validpassenger;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(String startpoint) {
        this.startpoint = startpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getRouteprice() {
        return routeprice;
    }

    public void setRouteprice(String routeprice) {
        this.routeprice = routeprice;
    }

    public String getNoofpassengers() {
        return noofpassengers;
    }

    public void setNoofpassengers(String noofpassengers) {
        this.noofpassengers = noofpassengers;
    }

    public String getInvalidpassenger() {
        return Invalidpassenger;
    }

    public void setInvalidpassenger(String invalidpassenger) {
        Invalidpassenger = invalidpassenger;
    }

    public String getTotalFare() {
        return TotalFare;
    }

    public void setTotalFare(String totalFare) {
        TotalFare = totalFare;
    }

    public String getTimeperiod() {
        return timeperiod;
    }

    public void setTimeperiod(String timeperiod) {
        this.timeperiod = timeperiod;
    }

    public String getValidpassenger() {
        return validpassenger;
    }

    public void setValidpassenger(String validpassenger) {
        this.validpassenger = validpassenger;
    }

    public RouteModel(String Id, String bus, String startp, String endp, String Routeprice){

        this.routeID=Id;
        this.bus=bus;
        this.startpoint=startp;
        this.endpoint=endp;
        this.routeprice=Routeprice;
    }



    public RouteModel(String Id, String bus,String day , String startp, String endp, String Routeprice,String noofpassengers,String totalFare, String timeperiod ){
        this.routeID=Id;
        this.bus=bus;
        this.day=day;
        this.startpoint=startp;
        this.endpoint=endp;
        this.routeprice=Routeprice;
        this.noofpassengers=noofpassengers;
        this.TotalFare=totalFare;
        this.timeperiod=timeperiod;

    }



}
