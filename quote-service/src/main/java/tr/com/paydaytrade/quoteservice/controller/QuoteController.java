package tr.com.paydaytrade.quoteservice.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tr.com.paydaytrade.quoteservice.model.Quote;
import tr.com.paydaytrade.quoteservice.model.dto.IexQuoteDto;
import tr.com.paydaytrade.quoteservice.model.dto.QuoteDto;
import tr.com.paydaytrade.quoteservice.service.MarketDataService;
import tr.com.paydaytrade.quoteservice.service.QuoteService;

import java.util.*;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private MarketDataService marketDataServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public QuoteController(QuoteService quoteService, MarketDataService marketDataServiceImpl) {
        this.quoteService = quoteService;
        this.marketDataServiceImpl = marketDataServiceImpl;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PutMapping(path = "/updateAll")
    @ResponseStatus(HttpStatus.OK)
    public void updateMarketData() {
        try {
            quoteService.updateMarketData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public void putQuote(@RequestBody QuoteDto quoteDto) {
        try {
            quoteService.updateQuote(modelMapper.map(quoteDto, Quote.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuote(@PathVariable String tickerId) {
        try {
            quoteService.createQuoteByTickerId(tickerId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<QuoteDto> getQuotesByTicker(@PathVariable String tickerId) {
        try {
            Set<Quote> quotes = quoteService.findQuoteByTicker(tickerId);
            Set<QuoteDto> quoteDtos = new HashSet<>();
            quotes.forEach(quote -> {
                QuoteDto quoteDto = modelMapper.map(quote, QuoteDto.class);
                quoteDtos.add(quoteDto);
            });
            return quoteDtos;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<Quote> getAllQuotes() {
        try {
            return quoteService.getAllQuotes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
