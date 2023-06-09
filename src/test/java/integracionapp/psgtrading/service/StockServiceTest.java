package integracionapp.psgtrading.service;

import integracionapp.psgtrading.configuration.WebSocketConfig;
import integracionapp.psgtrading.model.StockWrapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import yahoofinance.Stock;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @MockBean
    private WebSocketConfig webSocketConfig;
    @Test
    @Disabled("It is using yahoo, is old")
    void invoke_Stock() throws IOException {
        //find stock united utilities group
        final StockWrapper stock = stockService.findStock("PSG-USD");
        System.out.println(stock.getStock());

        final BigDecimal price = stockService.findPrice(stock);
        System.out.println(price);

        final BigDecimal change = stockService.findLastChangePercent(stock);
        System.out.println(change);

        final BigDecimal mean200DayPercent = stockService.findChangeFrom200MeanPerccent(stock);
        System.out.println(mean200DayPercent);
    }

    @Test
    @Disabled("It is using yahoo, is old")
    void invoke_Stocks() throws IOException, InterruptedException {
        final List<StockWrapper> stocks = stockService.findStocks(Arrays.asList("GOOG", "AMZN"));
        findPrices(stocks);

        Thread.sleep(16000);

        //find American Airlines
        final StockWrapper aa = stockService.findStock("UU.L");
        stocks.add(aa);
        //amazon and google should refresh and american airlines don´t
        System.out.println(stockService.findPrice(aa));
        findPrices(stocks);
        }
        private void findPrices( List<StockWrapper> stocks) throws IOException {
            for (StockWrapper stock : stocks) {
                System.out.println(stockService.findPrice(stock));
            }
        }

}
