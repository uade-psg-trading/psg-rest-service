package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.StockWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;

@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockService stockService;
    @Test
    void invoke_getStock() throws IOException {
        final StockWrapper stock = stockService.findStock("UU.L");
        System.out.println(stock.getStock());
        final BigDecimal price = stockService.findPrice(stock);
        System.out.println(price);
    }
}
