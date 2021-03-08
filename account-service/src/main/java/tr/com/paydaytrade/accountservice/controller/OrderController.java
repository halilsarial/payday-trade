package tr.com.paydaytrade.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tr.com.paydaytrade.accountservice.model.Order;
import tr.com.paydaytrade.accountservice.model.dto.OrderDto;
import tr.com.paydaytrade.accountservice.service.OrderService;

import java.util.Collections;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String putOrder(@RequestBody OrderDto orderDto) {
        try {
            return orderService.executeMarketOrder(orderDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
