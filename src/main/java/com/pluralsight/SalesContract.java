package com.pluralsight;

public class SalesContract extends Contract {
    private static final double SALES_TAX_RATE = 0.05;
    private static final double RECORDING_FEE = 100.00;
    private boolean financeOption;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean financeOption) {
        super(date, customerName, customerEmail, vehicleSold);
        this.financeOption = financeOption;
    }

    public double getSalesTaxAmount() {
        return getVehicleSold().getPrice() * SALES_TAX_RATE;
    }

    public double getRecordingFee() {
        return RECORDING_FEE;
    }

    public double getProcessingFee() {
        return getVehicleSold().getPrice() < 10000 ? 295.00 : 495.00;
    }

    public boolean isFinanceOption() { return financeOption; }
    public void setFinanceOption(boolean financeOption) { this.financeOption = financeOption; }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() + getSalesTaxAmount() + getRecordingFee() + getProcessingFee();
    }

    @Override
    public double getMonthlyPayment() {
        if (!financeOption) {
            return 0.0;
        }

        double totalPrice = getTotalPrice();
        int months;

        double rate;

        if (getVehicleSold().getPrice() >= 10000) {
            rate = 4.25;
            months = 48;
        } else {
            rate = 5.25;
            months = 24;
        }

        double monthlyRate = rate / 100 / 12;
        double payment = totalPrice * (monthlyRate * Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);
        return payment;
    }
}