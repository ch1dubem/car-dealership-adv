package com.pluralsight;

import java.io.*;

public class ContractFileManager {
    private static final String FILE_NAME = "contracts.csv";

    public void saveContract(Contract contract) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
            Vehicle vehicle = contract.getVehicleSold();
            StringBuilder sb = new StringBuilder();

            if (contract instanceof SalesContract) {
                SalesContract sc = (SalesContract) contract;
                sb.append("SALE|");
                sb.append(sc.getDate()).append("|");
                sb.append(sc.getCustomerName()).append("|");
                sb.append(sc.getCustomerEmail()).append("|");
                sb.append(vehicle.getVin()).append("|");
                sb.append(vehicle.getYear()).append("|");
                sb.append(vehicle.getMake()).append("|");
                sb.append(vehicle.getModel()).append("|");
                sb.append(vehicle.getVehicleType()).append("|");
                sb.append(vehicle.getColor()).append("|");
                sb.append(vehicle.getOdometer()).append("|");
                sb.append(String.format("%.2f", vehicle.getPrice())).append("|");
                sb.append(String.format("%.2f", sc.getSalesTaxAmount())).append("|");
                sb.append(String.format("%.2f", sc.getRecordingFee())).append("|");
                sb.append(String.format("%.2f", sc.getProcessingFee())).append("|");
                sb.append(String.format("%.2f", sc.getTotalPrice())).append("|");
                sb.append(sc.isFinanceOption() ? "YES" : "NO").append("|");
                sb.append(String.format("%.2f", sc.getMonthlyPayment()));

            } else if (contract instanceof LeaseContract) {
                LeaseContract lc = (LeaseContract) contract;
                sb.append("LEASE|");
                sb.append(lc.getDate()).append("|");
                sb.append(lc.getCustomerName()).append("|");
                sb.append(lc.getCustomerEmail()).append("|");
                sb.append(vehicle.getVin()).append("|");
                sb.append(vehicle.getYear()).append("|");
                sb.append(vehicle.getMake()).append("|");
                sb.append(vehicle.getModel()).append("|");
                sb.append(vehicle.getVehicleType()).append("|");
                sb.append(vehicle.getColor()).append("|");
                sb.append(vehicle.getOdometer()).append("|");
                sb.append(String.format("%.2f", vehicle.getPrice())).append("|");
                sb.append(String.format("%.2f", lc.getExpectedEndingValue())).append("|");
                sb.append(String.format("%.2f", lc.getLeaseFee())).append("|");
                sb.append(String.format("%.2f", lc.getTotalPrice())).append("|");
                sb.append(String.format("%.2f", lc.getMonthlyPayment()));
            }

            bw.write(sb.toString());
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving contract.");
        }
    }
}
