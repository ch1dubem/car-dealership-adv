package com.pluralsight;

import java.io.*;

public class DealershipFileManager {

    public Dealership getDealership() {
        Dealership dealership = new Dealership("Default Dealership", "Unknown Address", "Unknown Phone");

        File inventoryFile = new File("inventory.csv");
        if (!inventoryFile.exists()) {
            System.out.println("inventory.csv not found. Starting with empty inventory.");
            return dealership;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inventoryFile))) {
            String dealerLine = br.readLine();
            if (dealerLine == null || dealerLine.isBlank()) {
                System.out.println("inventory.csv is empty or malformed. Starting with empty inventory.");
                return dealership;
            }

            String[] dealerParts = dealerLine.split("\\|");
            if (dealerParts.length < 3) {
                System.out.println("inventory.csv dealer line is malformed. Starting with empty inventory.");
                return dealership;
            }
            dealership = new Dealership(dealerParts[0], dealerParts[1], dealerParts[2]);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length < 8) {
                    continue;
                }
                int vin = Integer.parseInt(parts[0]);
                int year = Integer.parseInt(parts[1]);
                String make = parts[2];
                String model = parts[3];
                String vehicleType = parts[4];
                String color = parts[5];
                int odometer = Integer.parseInt(parts[6]);
                double price = Double.parseDouble(parts[7]);

                dealership.addVehicle(new Vehicle(vin, year, make, model, vehicleType, color, odometer, price));
            }

        } catch (IOException e) {
            System.out.println("Error loading dealership file. Starting with empty inventory.");
        }
        return dealership;
    }

    public void saveDealership(Dealership dealership) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("inventory.csv"));
            bw.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());
            bw.newLine();

            for (Vehicle v : dealership.getAllVehicles()) {
                bw.write(v.getVin() + "|" + v.getYear() + "|" + v.getMake() + "|" + v.getModel() + "|"
                        + v.getVehicleType() + "|" + v.getColor() + "|" + v.getOdometer() + "|" + v.getPrice());
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving dealership file.");
        }
    }
}