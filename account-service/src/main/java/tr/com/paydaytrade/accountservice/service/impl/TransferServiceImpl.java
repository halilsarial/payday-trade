package tr.com.paydaytrade.accountservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.TraderUser;
import tr.com.paydaytrade.accountservice.service.AccountService;
import tr.com.paydaytrade.accountservice.service.TraderUserService;
import tr.com.paydaytrade.accountservice.service.TransferService;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private AccountService accountService;

    private TraderUserService traderUserService;

    @Autowired
    public TransferServiceImpl(AccountService accountService, TraderUserService traderUserService) {
        this.accountService = accountService;
        this.traderUserService = traderUserService;
    }

    @Override
    public String deposit(Long traderId, Double fund) {
        if (traderId == null) {
            throw new IllegalArgumentException("TraderId is not valid");
        }

        if (fund <= 0.0) {
            throw new IllegalArgumentException("Fund amount has to be greater than zero");
        }
        TraderUser traderUser = traderUserService.findById(traderId);
        if (traderUser == null) {
            throw new ResourceNotFoundException("Trader does not exists");
        }
        Account account = accountService.findAccountByTraderUser(traderUser);
        account.setAmount(account.getAmount() + fund);
        try {
            accountService.updateAccount(account);
            return "Deposit process has been successfully executed! Your new amount " + account.getAmount();
        } catch (IncorrectResultSizeDataAccessException e) {
            e.getLocalizedMessage();
        }
        return "Deposit process has been successfully executed!";
    }

    @Override
    public String withdraw(Long traderId, Double fund) {
        if (traderId == null) {
            throw new IllegalArgumentException("TraderId is not valid");
        }
        if (fund <= 0.0) {
            throw new IllegalArgumentException("Fund amount has to be greater than zero");
        }
        TraderUser traderUser = traderUserService.findById(traderId);
        if (traderUser == null) {
            throw new ResourceNotFoundException("Trader does not exists");
        }
        Account account = accountService.findAccountByTraderUser(traderUser);
        if (account.getAmount() < 0 || account.getAmount() < fund)
            throw new IllegalArgumentException("You don't have enough amount available to proceed with withdrawing");
        try {
            account.setAmount(account.getAmount() - fund);
            accountService.updateAccount(account);
            return "Withdraw process has been successfully executed! Your new amount " + account.getAmount();
        } catch (IncorrectResultSizeDataAccessException e) {
            e.getLocalizedMessage();
        }
        return "Withdraw process has been successfully executed!";
    }
}
