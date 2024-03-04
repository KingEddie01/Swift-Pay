package Fintech.Data.Repository;

import Dto.Request.TransactionHistoryRequest;
import Fintech.Data.Model.SwiftPay;

import java.util.ArrayList;
import java.util.List;

public class SwiftRepository implements SwiftRepo{

     List<SwiftPay> accountSaver = new ArrayList<>();
     List<String>transactionList = new ArrayList<>();


    @Override
    public void save(SwiftPay account) {
        accountSaver.add(account);
    }

    @Override
    public void deleteByAccountNumber(String accountNumber) {
        for (SwiftPay swiftPay : accountSaver){
            if (swiftPay.getAccountNumber().equals(accountNumber))
                accountSaver.remove(swiftPay);
        }
    }

    @Override
    public Long count() {
        return (long) accountSaver.size();
    }

    @Override
    public SwiftPay findByAccountNumber(String accountNumber) {
        for (SwiftPay swiftPay : accountSaver) {
            if (swiftPay.getAccountNumber().equals(accountNumber)) {
                return swiftPay;
            }
        }

        return null;
    }

    @Override
    public List<SwiftPay> findAll() {
        return new ArrayList<>(accountSaver);
    }

    @Override
    public void deleteAll() {
        accountSaver.clear();

    }

    @Override
    public void addTransaction(String transactionHistoryRequest) {
        transactionList.add(transactionHistoryRequest);


    }

    @Override
    public void deleteAllTransaction() {
        transactionList.clear();
    }
    @Override
    public List<String> displayAllTransaction() {
        return new ArrayList<>(transactionList);
    }
    public Long countTransaction() {
        return (long) transactionList.size();
    }
    public String convertToString(TransactionHistoryRequest transactionHistoryRequest) {
        if (transactionHistoryRequest.getTransactionType().equals("Withdrawal")) {
            return "Transaction Type : " + transactionHistoryRequest.getTransactionType() + '\n' +
                    "Amount : " + transactionHistoryRequest.getAmount() + '\n' +
                    "Balance : " + transactionHistoryRequest.getBalance() + '\n' +
                    "Date : " + transactionHistoryRequest.getDate();
        }
        if (transactionHistoryRequest.getTransactionType().equals("Transfer")) {
            return "Transaction Type : " + transactionHistoryRequest.getTransactionType() + '\n' +
                    "Amount : " + transactionHistoryRequest.getAmount() + '\n' +
                    "Recipient AccountName : " + transactionHistoryRequest.getReceiverAccountName() + '\n' +
                    "Recipient AccountNumber : " + transactionHistoryRequest.getReceiverAccountNumber() + '\n' +
                    "Balance : " + transactionHistoryRequest.getBalance() + '\n' +
                    "Date : " + transactionHistoryRequest.getDate();
        }
        return null;
    }
}
