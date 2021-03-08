package tr.com.paydaytrade.quoteservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import tr.com.paydaytrade.quoteservice.model.dto.IexQuoteDto;
import tr.com.paydaytrade.quoteservice.service.MarketDataService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarketDataServiceImpl implements MarketDataService {

    private final String BATCH_BASE_URL;
    private final String BATCH_TOKEN_URL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public MarketDataServiceImpl() {
        BATCH_BASE_URL = "https://cloud.iexapis.com/stable/stock/market/batch?symbols=";
        BATCH_TOKEN_URL = "&types=quote&token=pk_22792cfad91547bb99f9c84f1c5041e2";
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public Set<IexQuoteDto> getIexQuotes(List<String> symbols) {
        String symbolsInString = symbols.stream().map(String::valueOf).collect(Collectors.joining(","));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(BATCH_BASE_URL + symbolsInString + BATCH_TOKEN_URL, String.class);
        JSONObject obj = new JSONObject(responseEntity.getBody());
        Set<IexQuoteDto> iexQuotes = new HashSet<>();

        obj.keySet().forEach(key -> {
            String quote = (obj.getJSONObject(key)).get("quote").toString();
            try {
                IexQuoteDto iexQuoteDto = new ObjectMapper().readValue(quote, IexQuoteDto.class);
                iexQuotes.add(iexQuoteDto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return iexQuotes;
    }
    @Override
    public IexQuoteDto getIexQuote(String symbol) {
        Set<IexQuoteDto> quotesDtos = getIexQuotes(Collections.singletonList(symbol));
        if (CollectionUtils.isEmpty(quotesDtos) || quotesDtos.size() > 1) {
            throw new DataRetrievalFailureException("Unable to get data");
        }
        return quotesDtos.stream().findFirst().orElse(null);

    }
}
