# Advanced Car Dealership Application

## Description of the Project

This is a Java console application that extends the original car dealership system with full sales and leasing capabilities. Dealers can process vehicle sales and leases, calculate financing with taxes and fees, manage optional add-ons, and review contract history through a password-protected admin dashboard. All contracts are saved to a CSV file for persistent record keeping. The vehicle is automatically removed from inventory once a contract is confirmed. The app follows an object-oriented architecture using inheritance and abstraction, with an abstract Contract class extended by SalesContract and LeaseContract.

## User Stories

As a dealer, I want to sell or lease a vehicle by entering its VIN and customer info so that I can process transactions and automatically remove the vehicle from inventory.

As a dealer, I want sales tax, fees, and monthly payments automatically calculated based on whether it's a sale or lease so that pricing is accurate without manual math.

As a dealer, I want contracts saved to a file when confirmed so that all transaction records are stored permanently.

As a dealer, I want a password-protected menu to view all contracts or the last 10 so that I can review transaction history securely.

As a dealer, I want to select optional add-ons during a sale so that customers can customize their purchase and have it reflected in the total price.

## Setup

Instructions on how to set up and run the project using IntelliJ IDEA.

### Prerequisites

- IntelliJ IDEA: Ensure you have IntelliJ IDEA installed, which you can download from [here](https://www.jetbrains.com/idea/download/).
- Java SDK: Make sure Java SDK is installed and configured in IntelliJ.

### Running the Application in IntelliJ

Follow these steps to get your application running within IntelliJ IDEA:

1. Open IntelliJ IDEA.
2. Select "Open" and navigate to the directory where you cloned or downloaded the project.
3. After the project opens, wait for IntelliJ to index the files and set up the project.
4. Make sure `inventory.csv` exists in the project root folder with the dealership info on the first line and vehicles on the remaining lines.
5. Find the `Program` class in `src/main/java/com/pluralsight/`.
6. Right-click on the file and select 'Run Program.main()' to start the application.

## Technologies Used

- Java 17
- **Git & GitHub** for version control
- `java.io` — FileReader, BufferedReader, FileWriter, BufferedWriter for reading/writing CSV files
- `java.util` — ArrayList, Scanner for data storage and user input
- `java.time` — LocalDate, DateTimeFormatter for contract date handling
- `java.lang.Math` — Math.pow for monthly payment loan calculations

## Demo

Include screenshots or GIFs that show your application in action. Use tools like [Giphy Capture](https://giphy.com/apps/giphycapture) to record a GIF of your application.

<img width="800" height="662" alt="ScreenRecording2026-05-20at3 45 49AM-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/5af180d2-12e8-435f-bff6-af908e9b94db" />


## Future Work

Outline potential future enhancements or functionalities you might consider adding:

- Support multiple dealership locations with contracts all saved to the same file
- Add sorting options so contracts can be displayed by date, type, or total price
- Add the ability to search contracts by customer name or VIN
- Add a receipt printout that generates a formatted text file for each contract


## Resources

List resources such as tutorials, articles, or documentation that helped you during the project.

- [Java 17 ArrayList Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html)
- [Java 17 BufferedReader Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/BufferedReader.html)
- [W3Schools Java File Handling](https://www.w3schools.com/java/java_files.asp)
- https://raymaroun.github.io/yearup-java-visuals/

## Team Members

- **David Amah** - Solo developer. Built all features including Contract, SalesContract, LeaseContract, ContractFileManager, AddOn, AdminUserInterface, and updated UserInterface with full sell/lease workflow.

## Thanks

Express gratitude towards those who provided help, guidance, or resources:

- Thank you to Raymond for continuous support and guidance.
