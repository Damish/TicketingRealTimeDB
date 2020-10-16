package com.damishs.ticketingrealtimedb.Models;

public class Reader {

    String name;
    String readerLocation;

    public Reader() {
    }

    public Reader(String name, String readerLocation) {
        this.name = name;
        this.readerLocation = readerLocation;
    }

    public String getName() {
        return name;
    }

    public String getReaderLocation() {
        return readerLocation;
    }
}
