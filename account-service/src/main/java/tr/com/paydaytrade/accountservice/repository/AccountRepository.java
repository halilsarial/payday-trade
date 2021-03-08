package tr.com.paydaytrade.accountservice.repository;

import org.springframework.data.repository.CrudRepository;
import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.TraderUser;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByTraderUser(TraderUser traderUser);
}
