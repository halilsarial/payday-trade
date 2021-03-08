package tr.com.paydaytrade.accountservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.Portfolio;
import tr.com.paydaytrade.accountservice.model.TraderUser;
import tr.com.paydaytrade.accountservice.model.dto.AccountDto;
import tr.com.paydaytrade.accountservice.model.dto.TraderUserAccountDto;
import tr.com.paydaytrade.accountservice.model.dto.TraderUserDto;
import tr.com.paydaytrade.accountservice.repository.PortfolioRepository;
import tr.com.paydaytrade.accountservice.service.AccountService;
import tr.com.paydaytrade.accountservice.service.PortfolioService;
import tr.com.paydaytrade.accountservice.service.TraderUserService;

import java.util.List;

@Service
@Transactional
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private TraderUserService traderUserService;

    @Autowired
    private tr.com.paydaytrade.accountservice.service.PortfolioService portfolioService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public void createPortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio getPortfolioByAccount(Account account) {
        return portfolioRepository.getPortfolioByAccount(account);
    }

    @Override
    public TraderUserAccountDto getTraderAccount(Long traderId) {
        TraderUserAccountDto traderUserAccountDto = new TraderUserAccountDto();
        TraderUserDto traderUserDto = new TraderUserDto();
        AccountDto accountDto = new AccountDto();
        try {
            TraderUser traderUser = traderUserService.findById(traderId);
            traderUserDto.setFirstName(traderUser.getFirstName());
            traderUserDto.setLastName(traderUser.getLastName());
            traderUserDto.setEmail(traderUser.getEmail());
            traderUserDto.setDob(traderUser.getDob());
            Account account = accountService.findAccountByTraderUser(traderUser);
            accountDto.setAmount(account.getAmount());
            traderUserAccountDto.setTraderUserDto(traderUserDto);
            traderUserAccountDto.setAccountDto(accountDto);
        } catch (ResourceNotFoundException e) {
            e.getMessage();
        }

        return traderUserAccountDto;

    }
}
