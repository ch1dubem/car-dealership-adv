package com.pluralsight;

public class LeaseContract extends Contract {
    private static final double ENDING_VALUE = 0.50;
    private static final double LEASE_FEE = 0.07;
    private static final double LEASE_RATE = 4.0;
    private static final int LEASE_MONTHS = 36;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
    }

    public double getExpectedEndingValue() {
        return getVehicleSold().getPrice() * ENDING_VALUE;
    }

    public double getLeaseFee() {
        return getVehicleSold().getPrice() * LEASE_FEE;
    }

    @Override
    public double getTotalPrice() {
        return getExpectedEndingValue() + getLeaseFee();
    }

    @Override
    public double getMonthlyPayment() {
        double totalPrice = getTotalPrice();
        double monthlyRate = LEASE_RATE / 100 / 12;
        double payment = totalPrice * (monthlyRate * Math.pow(1 + monthlyRate, LEASE_MONTHS))
                / (Math.pow(1 + monthlyRate, LEASE_MONTHS) - 1);
        return payment;
    }
}
