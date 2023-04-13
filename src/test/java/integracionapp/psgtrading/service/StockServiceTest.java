package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.StockWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.Stock;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockService stockService;
    @Test
    void invoke_Stock() throws IOException {
        //find stock united utilities group
        final StockWrapper stock = stockService.findStock("UU.L");
        System.out.println(stock.getStock());

        final BigDecimal price = stockService.findPrice(stock);
        System.out.println(price);

        final BigDecimal change = stockService.findLastChangePercent(stock);
        System.out.println(change);

        final BigDecimal mean200DayPercent = stockService.findChangeFrom200MeanPerccent(stock);
        System.out.println(mean200DayPercent);
    }

    @Test
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
