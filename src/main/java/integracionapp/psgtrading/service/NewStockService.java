package integracionapp.psgtrading.service;


import integracionapp.psgtrading.dto.CoinResponseDto;
import integracionapp.psgtrading.dto.coinMarket.historical.HistoricalDataResponse;
import integracionapp.psgtrading.dto.coinMarket.latest.LatestDataResponse;
import integracionapp.psgtrading.dto.coinMarket.response.CoinDTO;
import integracionapp.psgtrading.dto.coinMarket.response.LatestDataResponseBuilder;
import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NewStockService {
    private final WebClient webClient;
    private String apiKey;
    private String host;

    public NewStockService(WebClient webClient,
                           @Value("${coinmarket.apikey}") String apiKey,
                           @Value("${coinmarket.host}") String host) {
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.host = host;
    }

    public CoinResponseDto getStockPrice(String symbol) {
        return webClient.get()
                .uri(host , uriBuilder -> uriBuilder.path("cryptocurrency/quotes/latest").queryParam("symbol",symbol).build())
                .header("X-CMC_PRO_API_KEY",apiKey)
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.OK)){
                        return response.bodyToMono(CoinResponseDto.class);
                    }else{
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                })
                .block();
    }

    public CoinDTO getCoinPrice(String symbol) {
        LatestDataResponse responsecm = webClient.get()
                .uri(host , uriBuilder -> uriBuilder.path("cryptocurrency/quotes/latest").queryParam("symbol",symbol).build())
                .header("X-CMC_PRO_API_KEY",apiKey)
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.OK)){
                        return response.bodyToMono(LatestDataResponse.class);
                    }else{
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                })
                .block();

        return new LatestDataResponseBuilder(responsecm, symbol).build();
    }

    public HistoricalDataResponse getHistoricalStockPrice(String symbol) {

        return webClient.get()
                .uri(host , uriBuilder -> uriBuilder.path("cryptocurrency/quotes/historical").queryParam("symbol",symbol).build())
                .header("X-CMC_PRO_API_KEY",apiKey)
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.OK)){
                        return response.bodyToMono(HistoricalDataResponse.class);
                    }else{
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                })
                .block();
    }

    public Mono getAllStock() {
        return webClient.get()
                .uri(host , uriBuilder -> uriBuilder.path("cryptocurrency/listings/latest")
                        .build())
                .header("X-CMC_PRO_API_KEY",apiKey)
                .retrieve()
                .bodyToFlux(Mono.class)
                .onErrorResume(exc -> Mono.error(new CustomRuntimeException(ErrorCode.GENERAL_ERROR, "There was a problem", exc)))
                .blockLast();
    }
}

