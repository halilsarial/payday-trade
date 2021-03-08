package tr.com.paydaytrade.accountservice.service;

import tr.com.paydaytrade.accountservice.model.TraderUser;
import tr.com.paydaytrade.accountservice.model.dto.DepositDto;

import java.util.Optional;

public interface TraderUserService {
    void createTraderUser(TraderUser traderUser);

    void updateTraderUser(TraderUser traderUser);

    void deleteTraderUser(TraderUser traderUser);

    TraderUser findById(Long id);

    TraderUser createTraderAndAccount(TraderUser traderUser);

    DepositDto getDepositByTraderId(Long traderId);
}
