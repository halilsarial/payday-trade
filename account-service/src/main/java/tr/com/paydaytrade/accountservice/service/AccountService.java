package tr.com.paydaytrade.accountservice.service;

import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.TraderUser;

public interface AccountService {
    Account findAccountByTraderUser(TraderUser traderUser);

    Account findAccountById(Long id);

    void createAccount(Account account);

    void updateAccount(Account account);
}
