package tr.com.paydaytrade.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import tr.com.paydaytrade.authenticationservice.domain.dto.OrderDto;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final String BATCH_BASE_URL = "http://localhost:8081/orders/";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(path = "/")
    public ResponseEntity<?> putOrder(@RequestBody OrderDto orderDto) {
        String response = restTemplate.postForObject(BATCH_BASE_URL, orderDto, String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
