package tr.com.paydaytrade.quoteservice.service;

import tr.com.paydaytrade.quoteservice.model.dto.IexQuoteDto;

import java.util.List;
import java.util.Set;

public interface MarketDataService {

    Set<IexQuoteDto> getIexQuotes(List<String> symbols);

    IexQuoteDto getIexQuote(String symbol);

}
