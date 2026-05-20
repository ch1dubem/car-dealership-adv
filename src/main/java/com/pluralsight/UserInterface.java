package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private Scanner scanner = new Scanner(System.in);
    private ContractFileManager contractFileManager = new ContractFileManager();

    public UserInterface() {}

    private void init() {
        DealershipFileManager manager = new DealershipFileManager();
        this.dealership = manager.getDealership();
    }

    public void display() {
        init();

        int choice = -1;
        while (choice != 99) {
            System.out.println("\n===== " + dealership.getName() + " =====");
            System.out.println("1 - Find vehicles within a price range");
            System.out.println("2 - Find vehicles by make/model");
            System.out.println("3 - Find vehicles by year range");
            System.out.println("4 - Find vehicles by color");
            System.out.println("5 - Find vehicles by mileage range");
            System.out.println("6 - Find vehicles by type");
            System.out.println("7 - List ALL vehicles");
            System.out.println("8 - Add a vehicle");
            System.out.println("9 - Remove a vehicle");
            System.out.println("10 - Sell/Lease a vehicle");
            System.out.println("11 - Admin");
            System.out.println("99 - Quit");
            System.out.print("Your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> processGetByPriceRequest();
                case 2 -> processGetByMakeModelRequest();
                case 3 -> processGetByYearRequest();
                case 4 -> processGetByColorRequest();
                case 5 -> processGetByMileageRequest();
                case 6 -> processGetByVehicleTypeRequest();
                case 7 -> processGetAllVehiclesRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 10 -> processSellLeaseRequest();
                case 11 -> processAdminRequest();
                case 99 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void displayVehicles(ArrayList<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }
        System.out.printf("%-10s %-6s %-10s %-10s %-8s %-8s %-10s %10s%n",
                "VIN", "Year", "Make", "Model", "Type", "Color", "Mileage", "Price");
        System.out.println("-".repeat(80));
        for (Vehicle v : vehicles) {
            System.out.printf("%-10d %-6d %-10s %-10s %-8s %-8s %-10d $%,.2f%n",
                    v.getVin(), v.getYear(), v.getMake(), v.getModel(),
                    v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
        }
    }

    public void processGetAllVehiclesRequest() {
        displayVehicles(dealership.getAllVehicles());
    }

    public void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter maximum price: ");
        double max = Double.parseDouble(scanner.nextLine().trim());
        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }

    public void processGetByMakeModelRequest() {
        System.out.println("Enter make: ");
        String make = scanner.nextLine().trim();
        System.out.println("Enter model: ");
        String model = scanner.nextLine().trim();
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    public void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter maximum year: ");
        int max = Integer.parseInt(scanner.nextLine().trim());
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }

    public void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine().trim();
        displayVehicles(dealership.getVehiclesByColor(color));
    }

    public void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        double min = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter maximum mileage: ");
        double max = Double.parseDouble(scanner.nextLine().trim());
        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type (car, truck, SUV, van): ");
        String type = scanner.nextLine().trim();
        displayVehicles(dealership.getVehiclesByType(type));
    }

    public void processAddVehicleRequest() {
        System.out.print("Enter VIN: ");
        int vin = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter make: ");
        String make = scanner.nextLine().trim();
        System.out.print("Enter model: ");
        String model = scanner.nextLine().trim();
        System.out.print("Enter type (car, truck, SUV, van): ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter color: ");
        String color = scanner.nextLine().trim();
        System.out.print("Enter odometer: ");
        int odometer = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        dealership.addVehicle(vehicle);

        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);
        System.out.println("Vehicle added!");
    }

    public void processRemoveVehicleRequest() {
        System.out.print("Enter VIN of vehicle to remove: ");
        int vin = Integer.parseInt(scanner.nextLine().trim());

        Vehicle toRemove = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                toRemove = v;
                break;
            }
        }

        if (toRemove != null) {
            dealership.removeVehicle(toRemove);
            DealershipFileManager manager = new DealershipFileManager();
            manager.saveDealership(dealership);
            System.out.println("Vehicle removed!");
        } else {
            System.out.println("Vehicle not found with VIN: " + vin);
        }
    }

    // ==================== Phase 3: Sell/Lease ====================

    public void processSellLeaseRequest() {
        try {
            System.out.print("Enter VIN of vehicle: ");
            int vin = Integer.parseInt(scanner.nextLine().trim());

            Vehicle vehicle = null;
            for (Vehicle v : dealership.getAllVehicles()) {
                if (v.getVin() == vin) {
                    vehicle = v;
                    break;
                }
            }

            if (vehicle == null) {
                System.out.println("Vehicle not found with VIN: " + vin);
                return;
            }

            System.out.printf("%nSelected: %d %s %s - $%,.2f%n",
                    vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getPrice());

            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Customer name cannot be empty.");
                return;
            }

            System.out.print("Enter customer email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty() || !email.contains("@")) {
                System.out.println("Please enter a valid email address.");
                return;
            }

            System.out.print("Sale or Lease? (S/L): ");
            String type = scanner.nextLine().trim().toUpperCase();
            if (!type.equals("S") && !type.equals("L")) {
                System.out.println("Invalid option. Please enter S or L.");
                return;
            }

            Contract contract;

            if (type.equals("L")) {
                int currentYear = LocalDate.now().getYear();
                if ((currentYear - vehicle.getYear()) > 3) {
                    System.out.println("Sorry, you can't lease a vehicle over 3 years old.");
                    return;
                }

                contract = new LeaseContract(date, name, email, vehicle);
                LeaseContract lc = (LeaseContract) contract;

                System.out.println("\n--- Lease Summary ---");
                System.out.printf("Vehicle Price:          $%,.2f%n", vehicle.getPrice());
                System.out.printf("Expected Ending Value:  $%,.2f%n", lc.getExpectedEndingValue());
                System.out.printf("Lease Fee:              $%,.2f%n", lc.getLeaseFee());
                System.out.printf("Total Price:            $%,.2f%n", lc.getTotalPrice());
                System.out.printf("Monthly Payment:        $%,.2f%n", lc.getMonthlyPayment());

            } else {
                System.out.print("Would you like to finance? (yes/no): ");
                String financeInput = scanner.nextLine().trim().toLowerCase();
                if (!financeInput.equals("yes") && !financeInput.equals("no")) {
                    System.out.println("Invalid option. Please enter yes or no.");
                    return;
                }
                boolean finance = financeInput.equals("yes");

                SalesContract sc = new SalesContract(date, name, email, vehicle, finance);

                // Bonus 2: offer add-ons
                processAddOns(sc);

                contract = sc;

                System.out.println("\n--- Sale Summary ---");
                System.out.printf("Vehicle Price:    $%,.2f%n", vehicle.getPrice());
                System.out.printf("Sales Tax (5%%):   $%,.2f%n", sc.getSalesTaxAmount());
                System.out.printf("Recording Fee:    $%,.2f%n", sc.getRecordingFee());
                System.out.printf("Processing Fee:   $%,.2f%n", sc.getProcessingFee());
                if (!sc.getAddOns().isEmpty()) {
                    System.out.println("Add-Ons:");
                    for (AddOn a : sc.getAddOns()) {
                        System.out.printf("  - %s%n", a);
                    }
                    System.out.printf("Add-Ons Total:    $%,.2f%n", sc.getAddOnsTotal());
                }
                System.out.printf("Total Price:      $%,.2f%n", sc.getTotalPrice());
                System.out.printf("Finance:          %s%n", finance ? "Yes" : "No");
                System.out.printf("Monthly Payment:  $%,.2f%n", sc.getMonthlyPayment());
            }

            System.out.print("\nConfirm this contract? (yes/no): ");
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("yes")) {
                contractFileManager.saveContract(contract);
                dealership.removeVehicle(vehicle);
                DealershipFileManager manager = new DealershipFileManager();
                manager.saveDealership(dealership);
                System.out.println("Contract saved! Vehicle removed from inventory.");
            } else {
                System.out.println("Contract cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // ==================== Bonus 2: Add-Ons ====================

    public void processAddOns(SalesContract sc) {
        ArrayList<AddOn> available = AddOn.getAvailableAddOns();

        System.out.println("\n--- Available Add-Ons ---");
        for (int i = 0; i < available.size(); i++) {
            System.out.printf("  %d - %s%n", (i + 1), available.get(i));
        }
        System.out.println("  0 - Done selecting");

        while (true) {
            System.out.print("Select an add-on (0 to finish): ");
            int pick;
            try {
                pick = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (pick == 0) break;
            if (pick < 1 || pick > available.size()) {
                System.out.println("Invalid selection.");
                continue;
            }

            AddOn selected = available.get(pick - 1);
            sc.addAddOn(selected);
            System.out.printf("Added: %s%n", selected.getName());
        }
    }

    // ==================== Bonus 1: Admin ====================

    public void processAdminRequest() {
        AdminUserInterface adminUI = new AdminUserInterface(scanner, contractFileManager);
        adminUI.display();
    }
}