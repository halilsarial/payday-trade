package tr.com.paydaytrade.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tr.com.paydaytrade.authenticationservice.domain.dto.QuoteDto;

import java.util.*;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private static final String BATCH_BASE_URL = "http://localhost:8082/quotes/";

    @Autowired
    private RestTemplate restTemplate;

    @PutMapping(path = "/updateAll")
    public ResponseEntity<?> updateAllQuotest() {
        restTemplate.put(BATCH_BASE_URL + "updateAll", new HashMap<>());
        return new ResponseEntity<>("All Quotes are updated successfully!", HttpStatus.OK);
    }

    @PutMapping(path = "/")
    public ResponseEntity<?> putQuote(@RequestBody QuoteDto quoteDto) {
        restTemplate.put(BATCH_BASE_URL, quoteDto);
        return new ResponseEntity<>("The Quote is updated successfully!", HttpStatus.OK);
    }

    @PostMapping(path = "/tickerId/{tickerId}")
    public ResponseEntity<?> createQuote(@PathVariable String tickerId) {
        restTemplate.postForLocation(BATCH_BASE_URL + "/tickerId/" + tickerId, new HashMap<>());
        return new ResponseEntity<>("The Quote is updated successfully!", HttpStatus.CREATED);
    }

    @GetMapping(path = "/tickerId/{tickerId}")
    public ResponseEntity<?> getQuotesByTicker(@PathVariable String tickerId) {
        QuoteDto[] quoteDtos = restTemplate.getForObject(BATCH_BASE_URL + "/tickerId/" + tickerId, QuoteDto[].class);
        return new ResponseEntity<>(quoteDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> getAllQuotes() {
        QuoteDto[] quoteDtos = restTemplate.getForObject(BATCH_BASE_URL, QuoteDto[].class);
        return new ResponseEntity<>(quoteDtos, HttpStatus.OK);
    }
}
