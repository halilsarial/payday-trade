package tr.com.paydaytrade.orderservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import tr.com.paydaytrade.orderservice.model.Order;
import tr.com.paydaytrade.orderservice.model.dto.OrderDto;
import tr.com.paydaytrade.orderservice.service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
    }

    public Order executeMarketOrder(List<OrderDto> orderDtos) {
        Double askPrice = 0.0;
        Double amount = 0.0;
        SecurityOrder securityOrder = new SecurityOrder();
        for (MarketOrderDto orderDto : orderDtos) {
            if (orderDto.getAccountId() < 0 || orderDto.getTicker() == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            securityOrder.setAccountId(orderDto.getAccountId());
            securityOrder.setSize(orderDto.getSize());
            securityOrder.setTicker(orderDto.getTicker());
            try {
                askPrice = (quoteDao.findById(orderDto.getTicker())).getAskPrice();
                logger.info(String.valueOf(askPrice));
                amount = (accountDao.findById(orderDto.getAccountId())).getAmount();
                logger.info(String.valueOf(amount));
            } catch (DataAccessException e) {
                e.getMessage();
            }
            if (orderDto.getSize() > 0)
                buy(amount, askPrice, securityOrder, orderDto);

            else
                sell(amount, askPrice, securityOrder, orderDto);

        }
        SecurityOrder securityOrder1 = securityOrderDao.save(securityOrder);
        return securityOrder1;
    }

    protected void buy(Double amount, Double askPrice, SecurityOrder securityOrder, MarketOrderDto orderDto) {
        Double securityCost = (orderDto.getSize() * askPrice);
        if (amount < securityCost) {
            securityOrder.setStatus(SecurityOrder.orderStatus.CANCELLED);
            securityOrder.setNotes("You have less money in account, minimum should be " + securityCost);
        } else {
            securityOrder.setPrice(askPrice);
            securityOrder.setStatus(SecurityOrder.orderStatus.FILLED);
            securityOrder.setNotes(null);
            accountDao.updateAccountbyID(amount - securityCost, orderDto.getAccountId());

        }

    }

    protected void sell(Double amount, Double askPrice, SecurityOrder securityOrder, MarketOrderDto orderDto) {
        Long pos = positionDao.getPosition(orderDto.getAccountId(), orderDto.getTicker());

        if (pos > -(orderDto.getSize())) {
            Double securityCost = -(orderDto.getSize() * askPrice);
            securityOrder.setPrice(askPrice);
            securityOrder.setStatus(SecurityOrder.orderStatus.FILLED);
            securityOrder.setNotes(null);
            accountDao.updateAccountbyID(amount + securityCost, orderDto.getAccountId());
        } else {
            securityOrder.setStatus(SecurityOrder.orderStatus.CANCELLED);
            securityOrder.setNotes("You have less security in account");
        }

    }
}
