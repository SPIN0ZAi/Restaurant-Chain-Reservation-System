package org.example.com.esii.eat.booking.core;

public class Table {
    public static final int CAPACITY = 6;
    private int occupiedSeats;
    private boolean isOccupied;
    private String reservationName;

    public Table() {
        this.isOccupied = false;
        this.occupiedSeats = 0;
        this.reservationName = null;
    }

    public void reserve(int numberOfPeople, String reservationName) {
        if (numberOfPeople > CAPACITY || numberOfPeople <= 0) {
            throw new IllegalArgumentException("Invalid number of people");
        }
        this.occupiedSeats = numberOfPeople;
        this.isOccupied = true;
        this.reservationName = reservationName;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    @Override
    public String toString() {
        return isOccupied ?
                String.format("Occupied (%d seats) by %s", occupiedSeats, reservationName) :
                "Available";
    }
}