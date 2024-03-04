package Fintech.Services;

import Dto.Request.*;
import Fintech.Data.Model.SwiftPay;
import Fintech.Data.Repository.SwiftRepo;
import Fintech.Data.Repository.SwiftRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SwiftService implements SwiftPayService {

    private SwiftRepo swiftRepo = new SwiftRepository();

    public SwiftService() {
        this.swiftRepo = new SwiftRepository();
    }

    @Override
    public String register(CreateAccountRequest createAccountRequest) {
        SwiftPay account = new SwiftPay();
        account.setPhoneNumber(createAccountRequest.getPhoneNumber());
        if (createAccountRequest == null || createAccountRequest.getPhoneNumber() == null || createAccountRequest.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Invalid input. Phone number cannot be null or empty.");
        }
        GenerateAccountNameRequest generateAccountNameRequest = new GenerateAccountNameRequest();
        generateAccountNameRequest.setFirstName(createAccountRequest.getFirstName());
        generateAccountNameRequest.setLastName(createAccountRequest.getLastName());
        account.setAccountName(generateAccountName(generateAccountNameRequest));
        account.setPin(createAccountRequest.getPin());
        account.setPhoneNumber(createAccountRequest.getPhoneNumber());
        account.setAccountNumber(generateAccountNumber(account.getPhoneNumber()));
        add(account);
        System.out.println("Your Account Number is: " + account.getAccountNumber());
        return "Account created successfully";
    }


    @Override
    public void deposit(DepositRequest depositRequest) {
        SwiftPay account = findAccount(depositRequest.getAccountNumber());
        double amount = depositRequest.getAmount();
        if (amount <= 0.0) {
            throw new IllegalArgumentException("Amount is invalid");
        }
        double balance = account.getBalance();
        balance += amount;
        account.setBalance(balance);
    }

    @Override
    public String withdraw(WithdrawRequest withdrawRequest) {
        SwiftPay swiftPay = findAccount(withdrawRequest.getAccountNumber());
        double amount = withdrawRequest.getAmount();
        double balance = swiftPay.getBalance();
        String pin = withdrawRequest.getPin();

        if (!pin.equals(swiftPay.getPin())) {
            throw new IllegalArgumentException("Incorrect PIN");
        }

        if (amount <= 0.0 || amount > balance) {
            throw new IllegalArgumentException("Invalid amount for withdrawal");
        }

        balance -= amount;
        swiftPay.setBalance(balance);


        TransactionHistoryRequest transactionHistoryRequest = new TransactionHistoryRequest();
        transactionHistoryRequest.setTransactionType("Withdraw");
        transactionHistoryRequest.setDate(LocalDate.now());
        transactionHistoryRequest.setAmount(amount);
        transactionHistoryRequest.setBalance(balance);
        transactionHistoryRequest.setTime(LocalTime.now());
        String details = convertTransactionToString(transactionHistoryRequest);

        if (withdrawRequest.getReceiverAccountNumber() == null) {
            addTransactions(details);
            return details;
        } else {
            return transactionHistoryRequest.setTransactionDetails("Transfer");
        }
    }

    @Override
    public double checkBalance(CheckBalanceRequest checkBalanceRequest) {
        SwiftPay account = findAccount(checkBalanceRequest.getAccountNumber());
        String pin = checkBalanceRequest.getPin();

        if (!pin.equals(account.getPin())) {
            throw new IllegalArgumentException("Incorrect PIN");
        }

        return account.getBalance();
    }


    @Override
    public String transfer(TransferRequest transferRequest) {
        try {
            SwiftPay sender = findAccount(transferRequest.getSenderAccountNumber());
            SwiftPay receiver = findAccount(transferRequest.getReceiverAccountNumber());


            if (sender.getAccountNumber().equals(receiver.getAccountNumber())) {
                throw new IllegalArgumentException("Sender and receiver accounts cannot be the same");
            }


            double amount = transferRequest.getAmount();
            if (sender.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient balance for transfer");
            }


            WithdrawRequest withdrawRequest = new WithdrawRequest();
            withdraw(withdrawRequest);


            DepositRequest depositRequest = new DepositRequest();
            deposit(depositRequest);


            TransactionHistoryRequest senderTransaction = new TransactionHistoryRequest();
            senderTransaction.setTransactionType("Transfer");
            senderTransaction.setDate(LocalDate.now());
            senderTransaction.setAmount(-amount);
            senderTransaction.setBalance(sender.getBalance());
            senderTransaction.setTime(LocalTime.now());


            TransactionHistoryRequest receiverTransaction = new TransactionHistoryRequest();
            receiverTransaction.setTransactionType("Transfer");
            receiverTransaction.setDate(LocalDate.now());
            receiverTransaction.setAmount(amount);
            receiverTransaction.setBalance(receiver.getBalance());
            receiverTransaction.setReceiverAccountName(sender.getAccountName());
            receiverTransaction.setReceiverAccountNumber(sender.getAccountNumber());
            receiverTransaction.setTime(LocalTime.now());

            addTransactions(convertTransactionToString(senderTransaction));
            addTransactions(convertTransactionToString(receiverTransaction));

            return "Transfer Successful!";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }


    public String generateAccountNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Invalid phoneNumber");
        }

        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
        if (phoneNumber.startsWith("234") && phoneNumber.length() == 13) {
            return phoneNumber.substring(3);
        } else if (phoneNumber.startsWith("0") && phoneNumber.length() == 11) {
            return phoneNumber.substring(1);
        } else {
            throw new IllegalArgumentException("Invalid phoneNumber");
        }
    }

    @Override
    public String generateAccountName(GenerateAccountNameRequest generateAccountNameRequest) {
        return generateAccountNameRequest.getFirstName() + " " + generateAccountNameRequest.getLastName();
    }

    @Override
    public SwiftPay findAccount(String accountNumber) {
        SwiftPay account = swiftRepo.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        return account;
    }

    @Override
    public Long count() {
        return swiftRepo.count();
    }

    public void add(SwiftPay swiftPay) {
        swiftRepo.save(swiftPay);
    }

    public String addTransactions(String transactionHistoryRequest) {
        swiftRepo.addTransaction(transactionHistoryRequest);
        return transactionHistoryRequest;
    }

    @Override
    public Long countTransaction() {
        return swiftRepo.countTransaction();
    }

    public List<String> displayAllTransaction() {
        return swiftRepo.displayAllTransaction();
    }

    @Override
    public String updatePin(UpdatePinRequest updatePinRequest) {
        SwiftPay account = swiftRepo.findByAccountNumber(updatePinRequest.getAccountNumber());
        String oldPin = updatePinRequest.getOldPin();
        String newPin = updatePinRequest.getNewPin();

        if (!oldPin.equals(account.getPin())) {
            throw new IllegalArgumentException("Incorrect Pin");
        }

        account.setPin(newPin);
        return account.getPin();
    }

    public String convertTransactionToString(TransactionHistoryRequest transactionHistoryRequest) {
        if (transactionHistoryRequest.getTransactionType().equals("Withdraw")) {
            return "\nTransaction Type : Withdraw Transaction" + '\n' +
                    "Amount Withdrawn : " + transactionHistoryRequest.getAmount() + '\n' +
                    "Balance : " + transactionHistoryRequest.getBalance() + '\n' +
                    "Date : " + transactionHistoryRequest.getDate() + '\n' +
                    "Time : " + transactionHistoryRequest.getTime();
        } else if (transactionHistoryRequest.getTransactionType().equals("Transfer")) {
            return "\nTransaction Type : Transfer Transaction" + '\n' +
                    "Amount Transferred: " + transactionHistoryRequest.getAmount() + '\n' +
                    "Recipient AccountName : " + transactionHistoryRequest.getReceiverAccountName() + '\n' +
                    "Recipient AccountNumber : " + transactionHistoryRequest.getReceiverAccountNumber() + '\n' +
                    "Balance : " + transactionHistoryRequest.getBalance() + '\n' +
                    "Date : " + transactionHistoryRequest.getDate() + '\n' +
                    "Time : " + transactionHistoryRequest.getTime();
        }
        return null;
    }
}
