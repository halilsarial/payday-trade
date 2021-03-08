package tr.com.paydaytrade.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tr.com.paydaytrade.accountservice.model.TraderUser;
import tr.com.paydaytrade.accountservice.model.dto.DepositDto;
import tr.com.paydaytrade.accountservice.model.dto.TraderUserAccountDto;
import tr.com.paydaytrade.accountservice.service.PortfolioService;
import tr.com.paydaytrade.accountservice.service.TraderUserService;
import tr.com.paydaytrade.accountservice.service.TransferService;

@Controller
@RequestMapping("/traders")
public class TraderUserController {

    @Autowired
    private TraderUserService traderUserServiceImpl;

    @Autowired
    private TransferService transferService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    public TraderUserController(TraderUserService traderUserServiceImpl, TransferService transferService) {
        this.traderUserServiceImpl = traderUserServiceImpl;
        this.transferService = transferService;
    }

    @DeleteMapping(path = "/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrader(@PathVariable Long traderId) {
        try {
            TraderUser traderUser = traderUserServiceImpl.findById(traderId);
            traderUserServiceImpl.deleteTraderUser(traderUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String createTraderUser(@RequestBody TraderUser traderUser) {
        try {
            traderUserServiceImpl.createTraderAndAccount(traderUser);
            return "Trader User created successfully!";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/{traderId}/deposit/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deposit(@PathVariable Long traderId, @PathVariable Double amount) {
        try {
            return transferService.deposit(traderId, amount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/{traderId}/withdraw/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String withdraw(@PathVariable Long traderId, @PathVariable Double amount) {
        try {
            return transferService.withdraw(traderId, amount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/{traderId}/portfolio/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DepositDto getPortfolio(@PathVariable Long traderId) {
        try {
            return traderUserServiceImpl.getDepositByTraderId(traderId);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping(path = "/{traderId}/profile")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TraderUserAccountDto getProfile(@PathVariable Long traderId) {
        try {
            return portfolioService.getTraderAccount(traderId);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}





