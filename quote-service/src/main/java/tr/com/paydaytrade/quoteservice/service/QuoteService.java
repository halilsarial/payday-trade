package tr.com.paydaytrade.quoteservice.service;

import tr.com.paydaytrade.quoteservice.model.Quote;
import tr.com.paydaytrade.quoteservice.model.dto.IexQuoteDto;

import java.util.List;
import java.util.Set;

public interface QuoteService {

    Quote buildQuoteFromIexQuote(IexQuoteDto iexQuoteDto);

    void initQuotes(List<String> tickers);

    void createQuoteByTickerId(String ticker);

    void updateMarketData();

    void createQuote(Quote quote);

    void deleteQuote(Quote quote);

    void updateQuote(Quote quote);

    Set<Quote> getAllQuotes();

    Set<String> getAllTickers();

    Quote findQuoteById(Long id);

    Set<Quote> findQuoteByTicker(String ticker);

}
