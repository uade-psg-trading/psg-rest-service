package integracionapp.psgtrading.service;

import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import integracionapp.psgtrading.model.StockWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StockService {

    private final RefreshService refreshService;
    public StockWrapper findStock(final String ticker) {
        try {
            return new StockWrapper(YahooFinance.get(ticker));
        } catch (IOException e) {
            throw new CustomRuntimeException(ErrorCode.GENERAL_ERROR, "Yahoo finance error");
        }
    }
    public BigDecimal findPrice(final StockWrapper stock) throws IOException {
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getPrice();
    }

    public List<StockWrapper> findStocks(final List<String> tickers) {
        return tickers.stream().map(this::findStock).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public BigDecimal findLastChangePercent(final StockWrapper stock) throws IOException {
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeInPercent();
    }

    public BigDecimal findChangeFrom200MeanPerccent(final StockWrapper stock) throws IOException {
        return stock.getStock().getQuote(refreshService.shouldRefresh(stock)).getChangeFromAvg200();
    }
}
