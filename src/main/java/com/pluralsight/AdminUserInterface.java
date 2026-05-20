package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminUserInterface {
    private static final String ADMIN_PASSWORD = "admin123";
    private final Scanner scanner;
    private final ContractFileManager contractFileManager;

    public AdminUserInterface(Scanner scanner, ContractFileManager contractFileManager) {
        this.scanner = scanner;
        this.contractFileManager = contractFileManager;
    }

    public void display() {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine().trim();

        if (!password.equals(ADMIN_PASSWORD)) {
            System.out.println("Incorrect password. Access denied.");
            return;
        }

        System.out.println("Access granted!\n");
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1 - List ALL contracts");
            System.out.println("2 - List last 10 contracts");
            System.out.println("0 - Back to main menu");
            System.out.print("Your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1 -> listAllContracts();
                case 2 -> listLastContracts();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listAllContracts() {
        ArrayList<Contract> contracts = contractFileManager.loadContracts();
        if (contracts.isEmpty()) {
            System.out.println("No contracts found.");
            return;
        }
        displayContracts(contracts);
    }

    private void listLastContracts() {
        ArrayList<Contract> contracts = contractFileManager.loadContracts();
        if (contracts.isEmpty()) {
            System.out.println("No contracts found.");
            return;
        }

        int start = Math.max(0, contracts.size() - 10);
        ArrayList<Contract> last = new ArrayList<>(contracts.subList(start, contracts.size()));
        System.out.printf("Showing last %d of %d contracts:%n", last.size(), contracts.size());
        displayContracts(last);
    }

    private void displayContracts(ArrayList<Contract> contracts) {
        System.out.println("=".repeat(80));
        for (Contract c : contracts) {
            Vehicle v = c.getVehicleSold();
            String type = (c instanceof SalesContract) ? "SALE" : "LEASE";

            System.out.printf("[%s] %s | %s (%s)%n", type, c.getDate(), c.getCustomerName(), c.getCustomerEmail());
            System.out.printf("  Vehicle: %d %s %s | VIN: %d | %s | %d mi | $%,.2f%n",
                    v.getYear(), v.getMake(), v.getModel(), v.getVin(),
                    v.getColor(), v.getOdometer(), v.getPrice());

            if (c instanceof SalesContract sc) {
                System.out.printf("  Tax: $%.2f | Recording: $%.2f | Processing: $%.2f | Finance: %s%n",
                        sc.getSalesTaxAmount(), sc.getRecordingFee(), sc.getProcessingFee(),
                        sc.isFinanceOption() ? "Yes" : "No");
            } else if (c instanceof LeaseContract lc) {
                System.out.printf("  Ending Value: $%.2f | Lease Fee: $%.2f%n",
                        lc.getExpectedEndingValue(), lc.getLeaseFee());
            }

            System.out.printf("  Total: $%,.2f | Monthly: $%,.2f%n", c.getTotalPrice(), c.getMonthlyPayment());
            System.out.println("-".repeat(80));
        }
    }
}
