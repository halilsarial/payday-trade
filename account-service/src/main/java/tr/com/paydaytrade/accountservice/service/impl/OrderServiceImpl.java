package tr.com.paydaytrade.accountservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.com.paydaytrade.accountservice.model.*;
import tr.com.paydaytrade.accountservice.model.dto.OrderDto;
import tr.com.paydaytrade.accountservice.model.dto.QuoteDto;
import tr.com.paydaytrade.accountservice.repository.OrderRepository;
import tr.com.paydaytrade.accountservice.service.AccountService;
import tr.com.paydaytrade.accountservice.service.EmailSenderService;
import tr.com.paydaytrade.accountservice.service.OrderService;
import tr.com.paydaytrade.accountservice.service.PortfolioService;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailSenderService emailSenderServiceImpl;

    @Override
    public String executeMarketOrder(OrderDto orderDto) {
        Double askPrice = 0.0;
        Double amount = 0.0;
        Order order = new Order();
        if (orderDto.getAccountId() < 0 || orderDto.getTicker() == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        Account account = accountService.findAccountById(orderDto.getAccountId());
        order.setAccount(account);
        order.setSize(orderDto.getSize());
        order.setTicker(orderDto.getTicker());
        try {
            QuoteDto[] quoteDtos = restTemplate.getForObject("http://localhost:8082/quotes/tickerId/" + orderDto.getTicker(), QuoteDto[].class);
            if (quoteDtos == null || quoteDtos.length < 1) {
                return null;
            }
            askPrice = (quoteDtos[0]).getAskPrice();
            logger.info(String.valueOf(askPrice));
            amount = account.getAmount();
            logger.info(String.valueOf(amount));
        } catch (DataAccessException e) {
            e.getMessage();
        }
        if (orderDto.getOrderType() == OrderType.BUY) {
            return buy(account.getTraderUser().getEmail(), amount, askPrice, order, orderDto);
        } else if (orderDto.getOrderType() == OrderType.SELL) {
            return sell(account.getTraderUser().getEmail(), amount, askPrice, order, orderDto);
        } else {
            return "Order Type is wrong!";
        }
    }

    protected String buy(String email, Double amount, Double askPrice, Order order, OrderDto orderDto) {
        Double securityCost = (orderDto.getSize() * askPrice);
        if (amount < securityCost) {
            order.setStatus(OrderStatus.CANCELLED);
            String message = "You have less money in account, minimum should be " + securityCost;
            order.setNotes(message);
            sendMail(email, message);
            return message;
        } else {
            order.setPrice(askPrice);
            order.setStatus(OrderStatus.FILLED);
            order.setNotes(null);
            Account account = accountService.findAccountById(orderDto.getAccountId());
            account.setAmount(amount - securityCost);
            Portfolio portfolio = new Portfolio();
            portfolio.setAccount(account);
            portfolio.setPosition(orderDto.getSize());
            portfolio.setTicker(orderDto.getTicker());
            accountService.updateAccount(account);
            portfolioService.createPortfolio(portfolio);
            return "You bought " + orderDto.getSize() + " " + orderDto.getTicker() + " SuccessFully!";
        }
    }

    private void sendMail(String email, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("xxxxx@gmail.com");
        mailMessage.setText(content);
        emailSenderServiceImpl.sendEmail(mailMessage);
    }

    protected String sell(String email, Double amount, Double askPrice, Order order, OrderDto orderDto) {
        Account account = accountService.findAccountById(orderDto.getAccountId());
        Portfolio portfolio = portfolioService.getPortfolioByAccount(account);

        if (portfolio.getPosition() > orderDto.getSize()) {
            Double securityCost = (orderDto.getSize() * askPrice);
            order.setPrice(askPrice);
            order.setStatus(OrderStatus.FILLED);
            order.setNotes(null);
            account.setAmount(amount + securityCost);
            accountService.updateAccount(account);
            String message = "You sold " + orderDto.getSize() + " " + orderDto.getTicker() + " SuccessFully!";
            sendMail(email, message);
            return message;
        } else {
            order.setStatus(OrderStatus.CANCELLED);
            order.setNotes("You have less security in account");
            return "You have less security in account";
        }
    }

    @Override
    public Order findByAccount(Account account) {
        return orderRepository.findByAccount(account).orElse(null);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }
}
