package Fintech.Data.Model;

import java.util.List;

public class SwiftPay {

    private String accountNumber;
    private String phoneNumber;
    private double balance;
    private String accountName;
    private String pin;

    public List<TransactionHistory> getTransactionHistoryList() {
        return transactionHistoryList;
    }

    public void setTransactionHistoryList(List<TransactionHistory> transactionHistoryList) {
        this.transactionHistoryList = transactionHistoryList;
    }

    private List<TransactionHistory> transactionHistoryList;

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("(\\+234|0)[789][01]\\d{8}$"))
            this.phoneNumber = phoneNumber;
        else
            throw new IllegalArgumentException("INVALID PHONE NUMBER");
        }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setAccountNumber(String phoneNumber){
        this.accountNumber = phoneNumber;
    }
    public String getAccountNumber(){
        return accountNumber;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getAccountName(){
        return accountName;
    }
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }




}
