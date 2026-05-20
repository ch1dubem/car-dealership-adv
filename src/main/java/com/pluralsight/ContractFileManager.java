package com.pluralsight;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager {
    private static final String FILE_NAME = "contracts.csv";

    public void saveContract(Contract contract) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
            Vehicle v = contract.getVehicleSold();
            StringBuilder sb = new StringBuilder();

            if (contract instanceof SalesContract) {
                SalesContract sc = (SalesContract) contract;
                sb.append("SALE|");
                sb.append(sc.getDate()).append("|");
                sb.append(sc.getCustomerName()).append("|");
                sb.append(sc.getCustomerEmail()).append("|");
                sb.append(v.getVin()).append("|");
                sb.append(v.getYear()).append("|");
                sb.append(v.getMake()).append("|");
                sb.append(v.getModel()).append("|");
                sb.append(v.getVehicleType()).append("|");
                sb.append(v.getColor()).append("|");
                sb.append(v.getOdometer()).append("|");
                sb.append(String.format("%.2f", v.getPrice())).append("|");
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
                sb.append(v.getVin()).append("|");
                sb.append(v.getYear()).append("|");
                sb.append(v.getMake()).append("|");
                sb.append(v.getModel()).append("|");
                sb.append(v.getVehicleType()).append("|");
                sb.append(v.getColor()).append("|");
                sb.append(v.getOdometer()).append("|");
                sb.append(String.format("%.2f", v.getPrice())).append("|");
                sb.append(String.format("%.2f", lc.getExpectedEndingValue())).append("|");
                sb.append(String.format("%.2f", lc.getLeaseFee())).append("|");
                sb.append(String.format("%.2f", lc.getTotalPrice())).append("|");
                sb.append(String.format("%.2f", lc.getMonthlyPayment()));
            }

            bw.write(sb.toString());
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving contract: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error while saving contract: " + e.getMessage());
        }
    }

    public ArrayList<Contract> loadContracts() {
        ArrayList<Contract> contracts = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    String[] parts = line.split("\\|");
                    String type = parts[0];

                    String date = parts[1];
                    String customerName = parts[2];
                    String customerEmail = parts[3];
                    int vin = Integer.parseInt(parts[4]);
                    int year = Integer.parseInt(parts[5]);
                    String make = parts[6];
                    String model = parts[7];
                    String vehicleType = parts[8];
                    String color = parts[9];
                    int odometer = Integer.parseInt(parts[10]);
                    double price = Double.parseDouble(parts[11]);

                    Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

                    if (type.equals("SALE")) {
                        boolean financeOption = parts[16].equalsIgnoreCase("YES");
                        SalesContract sc = new SalesContract(date, customerName, customerEmail, vehicle, financeOption);
                        contracts.add(sc);
                    } else if (type.equals("LEASE")) {
                        LeaseContract lc = new LeaseContract(date, customerName, customerEmail, vehicle);
                        contracts.add(lc);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Skipping line " + lineNumber + ": invalid number format.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Skipping line " + lineNumber + ": missing data fields.");
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            // No contracts file yet, that's okay
        } catch (IOException e) {
            System.out.println("Error reading contracts file: ");
        }

        return contracts;
    }
}