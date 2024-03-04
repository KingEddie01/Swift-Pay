package Dto.Request;

public class TransferRequest {
    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }
    private String pin;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    private String receiverAccountNumber;
    private double amount;
    private String senderPin;

    public String getSenderPin() {
        return senderPin;
    }

    public void setSenderPin(String senderPin) {
        this.senderPin = senderPin;
    }

    private String senderAccountNumber;




    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    private double balance;


}
