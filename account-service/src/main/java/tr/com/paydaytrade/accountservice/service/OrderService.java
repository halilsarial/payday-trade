package tr.com.paydaytrade.accountservice.service;

import tr.com.paydaytrade.accountservice.model.Account;
import tr.com.paydaytrade.accountservice.model.Order;
import tr.com.paydaytrade.accountservice.model.dto.OrderDto;

public interface OrderService {
    Order findByAccount(Account account);

    void deleteOrder(Order order);

    String executeMarketOrder(OrderDto orderDto);
}
