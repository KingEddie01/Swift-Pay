package Dto.Request;

public class DepositRequest {
    private double amount;
    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;}

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
