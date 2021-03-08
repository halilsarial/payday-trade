package tr.com.paydaytrade.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tr.com.paydaytrade.orderservice.model.Order;
import tr.com.paydaytrade.orderservice.model.dto.OrderDto;
import tr.com.paydaytrade.orderservice.service.OrderService;

import java.util.Collections;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/MarketOrder")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Order putOrder(@RequestBody OrderDto orderDto) {
        try {
            return orderService.executeMarketOrder(Collections.singletonList(orderDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
