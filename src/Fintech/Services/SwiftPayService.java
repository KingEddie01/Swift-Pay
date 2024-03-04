package Fintech.Services;

import Dto.Request.*;
import Fintech.Data.Model.SwiftPay;

import java.util.List;

public interface SwiftPayService {
    String register(CreateAccountRequest createAccountRequest);
    void deposit(DepositRequest depositRequest);
    String withdraw(WithdrawRequest withdrawRequest);
    double checkBalance(CheckBalanceRequest checkBalanceRequest);
    String transfer(TransferRequest transferRequest);
    String generateAccountNumber(String phoneNumber);

    String generateAccountName(GenerateAccountNameRequest generateAccountNameRequest);

    SwiftPay findAccount(String accountNumber);

    Long count();
    void add(SwiftPay swiftPay);
   String addTransactions(String transactionHistoryRequest);
    Long countTransaction();
    List<String> displayAllTransaction();

    String updatePin(UpdatePinRequest updatePinRequest);

    String convertTransactionToString(TransactionHistoryRequest transactionHistoryRequest);



}
