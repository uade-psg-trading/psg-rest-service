package integracionapp.psgtrading.service;

import integracionapp.psgtrading.dto.CoinResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NewStockServiceTest {

    @Autowired
    NewStockService newStockService;
    @Test
    @DisplayName("When the stock price is requested the response is a Double")
    void getStockPrice() {
        CoinResponseDto coinResponseDto = newStockService.getStockPrice();
        Double price = coinResponseDto.getData().getPSG().getQuote().getUSD().getPrice();
        assertTrue(price instanceof Double);
    }
}