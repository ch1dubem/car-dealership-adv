package com.pluralsight;

import java.util.ArrayList;

public class AddOn {
    private String name;
    private double price;

    public AddOn(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("%-25s $%.2f", name, price);
    }

    public static ArrayList<AddOn> getAvailableAddOns() {
        ArrayList<AddOn> addOns = new ArrayList<>();
        addOns.add(new AddOn("Nitrogen Tires", 149.99));
        addOns.add(new AddOn("Window Tinting", 299.99));
        addOns.add(new AddOn("All-Season Floor Mats", 89.99));
        addOns.add(new AddOn("Splash Guards", 124.99));
        addOns.add(new AddOn("Cargo Tray", 79.99));
        addOns.add(new AddOn("Wheel Locks", 59.99));
        return addOns;
    }
}