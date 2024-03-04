package Fintech.Data.Repository;

import Fintech.Data.Model.SwiftPay;

import java.util.List;

public interface SwiftRepo {
void save(SwiftPay account);
void deleteByAccountNumber(String account);
Long count();
SwiftPay findByAccountNumber(String accountNumber);
List<SwiftPay> findAll();
void deleteAll();

void addTransaction(String transactionHistoryRequest);

void deleteAllTransaction();

    List<String>displayAllTransaction();

Long countTransaction();





}
