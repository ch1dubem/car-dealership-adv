package com.pluralsight;

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

    public static AddOn[] getAvailableAddOns() {
        return new AddOn[] {
                new AddOn("Nitrogen Tires", 149.99),
                new AddOn("Window Tinting", 299.99),
                new AddOn("All-Season Floor Mats", 89.99),
                new AddOn("Splash Guards", 124.99),
                new AddOn("Cargo Tray", 79.99),
                new AddOn("Wheel Locks", 59.99)
        };
    }
}