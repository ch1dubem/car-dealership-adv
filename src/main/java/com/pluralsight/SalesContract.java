package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class SalesContract extends Contract {
    private static final double SALES_TAX_RATE = 0.05;
    private static final double RECORDING_FEE = 100.00;
    private boolean financeOption;
    private List<AddOn> addOns;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean financeOption) {
        super(date, customerName, customerEmail, vehicleSold);
        this.financeOption = financeOption;
        this.addOns = new ArrayList<>();
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

    public List<AddOn> getAddOns() { return addOns; }

    public void addAddOn(AddOn addOn) {
        addOns.add(addOn);
    }

    public double getAddOnsTotal() {
        double total = 0;
        for (AddOn a : addOns) {
            total += a.getPrice();
        }
        return total;
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() + getSalesTaxAmount() + getRecordingFee() + getProcessingFee() + getAddOnsTotal();
    }

    @Override
    public double getMonthlyPayment() {
        if (!financeOption) {
            return 0.0;
        }

        double totalPrice = getTotalPrice();
        int months;
        double annualRate;

        if (getVehicleSold().getPrice() >= 10000) {
            annualRate = 4.25;
            months = 48;
        } else {
            annualRate = 5.25;
            months = 24;
        }

        double monthlyRate = annualRate / 100 / 12;
        double payment = totalPrice * (monthlyRate * Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);
        return payment;
    }
}