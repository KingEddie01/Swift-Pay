package Fintech.Controller;

import Dto.Request.*;
import Fintech.Services.SwiftService;

import java.util.Scanner;

public class SwiftPayController {
    private SwiftService swiftService = new SwiftService();
    private final Scanner scanner = new Scanner(System.in);

    private final DepositRequest depositRequest = new DepositRequest();
    private final CreateAccountRequest createAccountRequest = new CreateAccountRequest();
    private final TransferRequest transferRequest = new TransferRequest();


    public SwiftPayController() {
        this.swiftService = new SwiftService();

    }

    public void start() {
        System.out.println("Welcome to SwiftPay!");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Register Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Transfer");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    transfer();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerAccount() {
        try {
            System.out.println("Enter First Name:");
            String firstName = scanner.nextLine();
            createAccountRequest.setFirstName(firstName);

            System.out.println("Enter Last Name:");
            String lastName = scanner.nextLine();
            createAccountRequest.setLastName(lastName);

            System.out.println("Enter PIN:");
            String pin = scanner.nextLine();
            createAccountRequest.setPin(pin);

            System.out.println("Enter Phone Number:");
            String phoneNumber = scanner.nextLine();
            createAccountRequest.setPhoneNumber(phoneNumber);
            String account = swiftService.register(createAccountRequest);
            System.out.println(account);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deposit() {
        try {
            System.out.println("Enter Account Number:");
            String accountNumber = scanner.next();
            depositRequest.setAccountNumber(accountNumber);
            System.out.println("Enter Amount to Deposit:");
            double amount = scanner.nextDouble();
            depositRequest.setAmount(amount);
            swiftService.deposit(depositRequest);
            System.out.println("Deposit Successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void withdraw() {
        try {
            WithdrawRequest withdrawRequest = new WithdrawRequest();
            withdrawRequest.setAccountNumber(depositRequest.getAccountNumber());
            System.out.println("Enter Amount to Withdraw:");
            double amount = scanner.nextDouble();
            withdrawRequest.setAmount(amount);

            System.out.println("Enter PIN:");
            String pin = scanner.next();
            withdrawRequest.setPin(pin);

            String transactionDetails = swiftService.withdraw(withdrawRequest);
            System.out.println("Withdrawal Successful!");
            System.out.println("Transaction Details: " + transactionDetails);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void checkBalance() {
        try {
            CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
            checkBalanceRequest.setAccountNumber(depositRequest.getAccountNumber());
            System.out.println("Enter PIN:");
            String pin = scanner.nextLine();
            checkBalanceRequest.setPin(pin);
            double balance = swiftService.checkBalance(checkBalanceRequest);
            System.out.println("Your Account Balance is: " + balance);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void transfer() {
        try {
            TransferRequest transferRequest = new TransferRequest();
           transferRequest.setSenderAccountNumber(depositRequest.getAccountNumber());

            System.out.println("Enter Receiver Account Number:");
            String receiverAccountNumber = scanner.nextLine();
            transferRequest.setReceiverAccountNumber(receiverAccountNumber);
            System.out.println("Enter Amount to Transfer:");
            double amount = scanner.nextDouble();
            transferRequest.setAmount(amount);

            System.out.println("Enter PIN:");
            String pin = scanner.nextLine();
            transferRequest.setPin(pin);

            String transactionDetails = swiftService.transfer(transferRequest);
            System.out.println("Transfer Successful!");
            System.out.println("Transaction Details: " + transactionDetails);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwiftPayController controller = new SwiftPayController();
        controller.start();
    }
}
