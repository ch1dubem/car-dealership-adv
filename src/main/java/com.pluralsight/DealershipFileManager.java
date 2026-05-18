package com.pluralsight;

import java.io.*;

public class DealershipFileManager {

    public Dealership getDealership() {
        Dealership dealership = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("inventory.csv"));

            // First line is dealership info
            String dealerLine = br.readLine();
            String[] dealerParts = dealerLine.split("\\|");
            dealership = new Dealership(dealerParts[0], dealerParts[1], dealerParts[2]);

            // Remaining lines are vehicles
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
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
            br.close();

        } catch (IOException e) {
            System.out.println("Error loading dealership file.");
        }
        return dealership;
    }

    public void saveDealership(Dealership dealership) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("inventory.csv"));
            bw.write(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());
            bw.newLine();

            for (Vehicle v : dealership.getAllVehicles()) {
                bw.write(v.getVin() + "|" + v.getYear() + "|" + v.getMake() + "|" + v.getModel() + "|" + v.getVehicleType() + "|" + v.getColor() + "|" + v.getOdometer() + "|" + v.getPrice());
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving dealership file.");
        }
    }
}