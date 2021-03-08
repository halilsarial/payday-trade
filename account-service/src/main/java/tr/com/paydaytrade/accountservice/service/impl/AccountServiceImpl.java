package tr.com.paydaytrade.accountservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.TraderUser;
import tr.com.paydaytrade.accountservice.repository.AccountRepository;
import tr.com.paydaytrade.accountservice.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findAccountByTraderUser(TraderUser traderUser) {
        return accountRepository.findAccountByTraderUser(traderUser).orElse(null);
    }

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }
}
