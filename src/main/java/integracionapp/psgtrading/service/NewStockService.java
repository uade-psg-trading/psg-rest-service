package integracionapp.psgtrading.service;


import integracionapp.psgtrading.dto.CoinResponseDto;
import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
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

    public CoinResponseDto getStockPrice() {
        return webClient.get()
                .uri(host , uriBuilder -> uriBuilder.path("cryptocurrency/quotes/latest").queryParam("symbol","PSG").build())
                .header("X-CMC_PRO_API_KEY",apiKey)
                .retrieve()
                .bodyToMono(CoinResponseDto.class)
                .onErrorResume(exc -> Mono.error(new CustomRuntimeException(ErrorCode.GENERAL_ERROR, "There was a problem", exc)))
                .block();
    }
}

