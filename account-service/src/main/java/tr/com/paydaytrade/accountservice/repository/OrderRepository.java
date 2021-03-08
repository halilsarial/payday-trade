package tr.com.paydaytrade.accountservice.repository;

import org.springframework.data.repository.CrudRepository;
import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.Order;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByAccount(Account account);
}
