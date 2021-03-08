package tr.com.paydaytrade.accountservice.repository;

import org.springframework.data.repository.CrudRepository;
import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.Portfolio;

public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
    Portfolio getPortfolioByAccount(Account account);
}
