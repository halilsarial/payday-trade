package tr.com.paydaytrade.authenticationservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ticker",
        "portfolio",
        "quote"
})
public class DepositDto {

    @JsonProperty("ticker")
    private String ticker;
    @JsonProperty("portfolio")
    private PortfolioDto portfolio;
    @JsonProperty("quote")
    private QuoteDto quote;

    @JsonProperty("ticker")
    public String getTicker() {
        return ticker;
    }

    @JsonProperty("ticker")
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @JsonProperty("portfolio")
    public PortfolioDto getPortfolio() {
        return portfolio;
    }

    @JsonProperty("portfolio")
    public void setPortfolio(PortfolioDto portfolio) {
        this.portfolio = portfolio;
    }

    @JsonProperty("quote")
    public QuoteDto getQuote() {
        return quote;
    }

    @JsonProperty("quote")
    public void setQuote(QuoteDto quote) {
        this.quote = quote;
    }

}