package org.example.com.esii.eat.booking.core;

public class Chain {
    private Restaurant[] restaurants;
    private int numberOfRestaurants;
    private String chainName;

    public Chain(String chainName, int maxRestaurants) {
        this.chainName = chainName;
        this.restaurants = new Restaurant[maxRestaurants];
        this.numberOfRestaurants = 0;
    }

    public boolean addRestaurant(Restaurant restaurant) {
        if (numberOfRestaurants >= restaurants.length) {
            return false;
        }
        for (int i = 0; i < restaurants.length; i++) {
            if (restaurants[i] == null) {
                restaurants[i] = restaurant;
                numberOfRestaurants++;
                return true;
            }
        }
        return false;
    }

    public boolean reserveRestaurant(int numberOfPeople, String restaurantName, String reservationName) {
        Restaurant restaurant = getRestaurant(restaurantName);
        return restaurant != null && restaurant.reserveTables(numberOfPeople, reservationName);
    }

    public Restaurant getRestaurant(String name) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant != null && restaurant.getName().equals(name)) {
                return restaurant;
            }
        }
        return null;
    }

    public Restaurant searchRestaurant(int numberOfPeople, String restaurantName) {
        int startPos = getRestaurantPosition(restaurantName);
        if (startPos == -1) return null;

        int currentPos = startPos;
        do {
            Restaurant current = restaurants[currentPos];
            if (current != null && current.hasAvailableTables(numberOfPeople)) {
                return current;
            }
            currentPos = (currentPos + 1) % restaurants.length;
        } while (currentPos != startPos);

        return null;
    }

    private int getRestaurantPosition(String name) {
        for (int i = 0; i < restaurants.length; i++) {
            if (restaurants[i] != null && restaurants[i].getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}