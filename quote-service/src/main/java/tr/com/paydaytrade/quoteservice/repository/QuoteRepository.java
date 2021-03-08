package tr.com.paydaytrade.quoteservice.repository;

import org.springframework.data.repository.CrudRepository;
import tr.com.paydaytrade.quoteservice.model.Quote;

import java.util.Set;

public interface QuoteRepository extends CrudRepository<Quote, Long> {
    Set<Quote> findByTicker(String ticker);
}
