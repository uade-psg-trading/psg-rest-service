package integracionapp.psgtrading.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import integracionapp.psgtrading.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewStockServiceTest {

  @Mock NewStockService newStockService;

  @Test
  @DisplayName("When the stock price is requested the response is a Double")
  void getStockPrice() {

    when(newStockService.getStockPrice()).thenReturn(getCoinResponse());
    
    CoinResponseDto coinResponseDto = newStockService.getStockPrice();
    Double price = coinResponseDto.getData().getPSG().getQuote().getUSD().getPrice();
    assertTrue(price instanceof Double);
  }

  private CoinResponseDto getCoinResponse() {
    return CoinResponseDto.builder().data(getData()).build();
  }

  private Data getData() {
    return Data.builder().PSG(getPSG()).build();
  }

  private PSG getPSG() {
    return PSG.builder().quote(getQuote()).build();
  }

  private Quote getQuote() {
    return Quote.builder().USD(getUSD()).build();
  }

  private USD getUSD() {
    return USD.builder().price(0.2786791204567678).build();
  }
}
