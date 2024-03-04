package Dto.Request;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionHistoryRequest {
    private String transactionType;
    private double amount;
    private double balance;

    private LocalDate date;

    private LocalTime time;

    private String receiverAccountName;

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    private String receiverAccountNumber;

    private String transactionDetails;

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public String setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
        return transactionDetails;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public String getReceiverAccountName() {
        return receiverAccountName;
    }

    public void setReceiverAccountName(String receiverAccountName) {
        this.receiverAccountName = receiverAccountName;
    }

    @Override
    public String toString() {
        if (transactionType.equals("Withdrawal")){
            return "Transaction Type : " + transactionType + '\n' +
                    "Amount : " + amount + '\n' +
                    "Balance : " + balance + '\n' +
                    "Date : " + date;
        }
        if (transactionType.equals("Transfer"))
            return "Transaction Type : " + transactionType + '\n' +
                    "Amount : " + amount + '\n' +
                    "Recipient AccountName : " + receiverAccountName + '\n' +
                    "Recipient AccountNumber : " + receiverAccountNumber+ '\n' +
                    "Balance : " + balance + '\n' +
                    "Date : " + date;
        return null;
    }




    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
