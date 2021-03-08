package tr.com.paydaytrade.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tr.com.paydaytrade.authenticationservice.domain.dto.DepositDto;
import tr.com.paydaytrade.authenticationservice.domain.dto.TraderUserAccountDto;
import tr.com.paydaytrade.authenticationservice.domain.dto.TraderUserDto;

import java.util.HashMap;

@Controller
@RequestMapping("/traders")
public class TraderUserController {

    private static final String BATCH_BASE_URL = "http://localhost:8081/traders/";

    @Autowired
    private RestTemplate restTemplate;

    @DeleteMapping(path = "/{traderId}")
    public ResponseEntity<?> deleteTrader(@PathVariable Long traderId) {
        restTemplate.delete(BATCH_BASE_URL);
        return new ResponseEntity<>("Trader User was deleted successfully!", HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<?> createTraderUser(@RequestBody TraderUserDto traderUserDto) {
        String response = restTemplate.postForObject(BATCH_BASE_URL, traderUserDto, String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/{traderId}/deposit/amount/{amount}")
    public ResponseEntity<?> deposit(@PathVariable Long traderId, @PathVariable Double amount) {
        restTemplate.put(BATCH_BASE_URL + traderId + "/deposit/amount/" + amount, new HashMap<>());
        return new ResponseEntity<>("Deposit process has been successfully executed!", HttpStatus.OK);
    }

    @PutMapping(path = "/{traderId}/withdraw/amount/{amount}")
    public ResponseEntity<?> withdraw(@PathVariable Long traderId, @PathVariable Double amount) {
        restTemplate.put(BATCH_BASE_URL + traderId + "/deposit/amount/" + amount, new HashMap<>());
        return new ResponseEntity<>("Withdraw process has been successfully executed!", HttpStatus.OK);
    }

    @GetMapping(path = "/{traderId}/profile")
    public ResponseEntity<?> getProfile(@PathVariable Long traderId) {
        DepositDto[] response = restTemplate.getForObject(BATCH_BASE_URL + traderId + "/profile", DepositDto[].class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}





