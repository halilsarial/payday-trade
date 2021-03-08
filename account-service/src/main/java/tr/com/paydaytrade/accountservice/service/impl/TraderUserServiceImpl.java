package tr.com.paydaytrade.accountservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.Portfolio;
import tr.com.paydaytrade.accountservice.model.TraderUser;
import tr.com.paydaytrade.accountservice.model.dto.DepositDto;
import tr.com.paydaytrade.accountservice.model.dto.QuoteDto;
import tr.com.paydaytrade.accountservice.repository.TraderUserRepository;
import tr.com.paydaytrade.accountservice.service.AccountService;
import tr.com.paydaytrade.accountservice.service.PortfolioService;
import tr.com.paydaytrade.accountservice.service.TraderUserService;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TraderUserServiceImpl implements TraderUserService {

    @Autowired
    private TraderUserRepository traderUserRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void createTraderUser(TraderUser traderUser) {
        traderUserRepository.save(traderUser);
    }

    @Override
    public void updateTraderUser(TraderUser traderUser) {
        traderUserRepository.save(traderUser);
    }

    @Override
    public void deleteTraderUser(TraderUser traderUser) {
        traderUserRepository.delete(traderUser);
    }

    @Override
    public TraderUser findById(Long id) {
        return traderUserRepository.findById(id).orElse(null);
    }

    @Override
    public TraderUser createTraderAndAccount(TraderUser traderUser) {
        traderUserRepository.save(traderUser);
        Account account = new Account();
        account.setAmount(0.0);
        account.setTraderUser(traderUser);
        accountService.createAccount(account);
        return traderUser;
    }

    public DepositDto getDepositByTraderId(Long traderId) {
        DepositDto depositDto = new DepositDto();
        TraderUser traderUser = findById(traderId);
        Account account = accountService.findAccountByTraderUser(traderUser);
        Portfolio portfolio = portfolioService.getPortfolioByAccount(account);
        QuoteDto[] quoteDtos = restTemplate.getForObject("http://localhost:8082/quotes/tickerId/" + portfolio.getTicker(), QuoteDto[].class);
        if (quoteDtos == null || quoteDtos.length < 1) {
            return null;
        }
        depositDto.setQuote(quoteDtos[0]);
        depositDto.setPortfolio(portfolio);
        depositDto.setTicker(portfolio.getTicker());
        return depositDto;
    }
}
