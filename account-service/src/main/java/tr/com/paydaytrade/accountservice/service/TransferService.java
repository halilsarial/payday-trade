package tr.com.paydaytrade.accountservice.service;

import tr.com.paydaytrade.accountservice.model.Account;

public interface TransferService {
    String deposit(Long traderId, Double fund);
    String withdraw(Long traderId, Double fund);
}
