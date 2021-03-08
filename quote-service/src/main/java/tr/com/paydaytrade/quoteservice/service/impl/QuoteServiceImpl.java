package tr.com.paydaytrade.quoteservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.paydaytrade.quoteservice.model.Quote;
import tr.com.paydaytrade.quoteservice.model.dto.IexQuoteDto;
import tr.com.paydaytrade.quoteservice.repository.QuoteRepository;
import tr.com.paydaytrade.quoteservice.service.MarketDataService;
import tr.com.paydaytrade.quoteservice.service.QuoteService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private MarketDataService marketDataServiceImpl;

    @Autowired
    public QuoteServiceImpl(QuoteRepository quoteRepository, MarketDataService marketDataServiceImpl) {
        this.quoteRepository = quoteRepository;
        this.marketDataServiceImpl = marketDataServiceImpl;
    }

    @Override
    public Quote buildQuoteFromIexQuote(IexQuoteDto iexQuoteDto) {
        if (iexQuoteDto == null || iexQuoteDto.getLatestPrice() == null) {
            throw new IllegalArgumentException("Quote is Null");
        }
        Quote quote = new Quote();
        quote.setTicker(iexQuoteDto.getSymbol());
        quote.setLastPrice(iexQuoteDto.getLatestPrice());
        quote.setAskPrice(iexQuoteDto.getIexAskPrice());
        quote.setAskSize(iexQuoteDto.getIexAskSize());
        quote.setBidSize(iexQuoteDto.getIexBidSize());
        quote.setBidPrice(iexQuoteDto.getIexBidPrice());
        return quote;
    }

    public void initQuotes(List<String> tickers) {

        for (String ticker : tickers) {
            if (findQuoteByTicker(ticker) == null) {
                throw new IllegalArgumentException("Ticker already exists");
            } else {
                IexQuoteDto iexQuoteDto = marketDataServiceImpl.getIexQuote(ticker);
                if (iexQuoteDto == null) {
                    throw new ResourceNotFoundException("Resource not found");
                }
                quoteRepository.save(buildQuoteFromIexQuote(iexQuoteDto));
            }
        }

    }

    public void createQuoteByTickerId(String ticker) {
        initQuotes(Collections.singletonList(ticker));
    }

    public void updateMarketData() {
        Set<String> tickers = new HashSet<>();
        try {
            tickers = getAllTickers();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        for (String ticker : tickers) {
            Set<Quote> quotes = quoteRepository.findByTicker(ticker);
            quotes.forEach(quote -> {
                quoteRepository.delete(quote);
            });
            createQuoteByTickerId(ticker);
        }
    }

    @Override
    public void createQuote(Quote quote) {
        quoteRepository.save(quote);
    }

    @Override
    public void deleteQuote(Quote quote) {
        quoteRepository.delete(quote);
    }

    @Override
    public void updateQuote(Quote quote) {
        quoteRepository.save(quote);
    }

    @Override
    public Set<Quote> getAllQuotes() {
        return StreamSupport.stream(quoteRepository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllTickers() {
        Iterable<Quote> quotes = quoteRepository.findAll();
        Set<String> tickers = new HashSet<>();
        StreamSupport.stream(quotes.spliterator(), false).forEach(quote -> tickers.add(quote.getTicker()));
        return tickers;
    }

    @Override
    public Quote findQuoteById(Long id) {
        return quoteRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Quote> findQuoteByTicker(String ticker) {
        return quoteRepository.findByTicker(ticker);
    }

}
