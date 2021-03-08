package tr.com.paydaytrade.accountservice.service;

import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.Portfolio;
import tr.com.paydaytrade.accountservice.model.TraderUser;
import tr.com.paydaytrade.accountservice.model.dto.TraderUserAccountDto;

import java.util.List;

public interface PortfolioService {

    void createPortfolio(Portfolio portfolio);

    Portfolio getPortfolioByAccount(Account account);

    TraderUserAccountDto getTraderAccount(Long traderId);
}
