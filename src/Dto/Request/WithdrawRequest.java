package Dto.Request;

public class WithdrawRequest {
    private double amount;
    private String pin;
    private String accountNumber;

    private String receiverAccountNumber;

    private String OwnerAccount;

    public String getOwnerAccount() {
        return OwnerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        OwnerAccount = ownerAccount;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
