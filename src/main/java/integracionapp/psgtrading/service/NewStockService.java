package integracionapp.psgtrading.service;

import integracionapp.psgtrading.dto.coins.USD;
import integracionapp.psgtrading.dto.coins.latest.LatestDataResponse;
import integracionapp.psgtrading.model.TokenPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
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
    public TokenPrice getCoinPrice(String symbol) {
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

        if(responsecm == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to get prices for " + symbol);
        }

        TokenPrice tokenPrice = new TokenPrice();
        USD usd = responsecm.getData().get(symbol).getQuote().getUSD();
        tokenPrice.setPrice(usd.getPrice());
        tokenPrice.setVolume24h(usd.getVolume_24h());
        tokenPrice.setVolumeChange24h(usd.getVolume_change_24h());
        tokenPrice.setPercentChange1h(usd.getPercent_change_1h());
        tokenPrice.setPercentChange24h(usd.getPercent_change_24h());
        tokenPrice.setPercentChange7d(usd.getPercent_change_7d());
        tokenPrice.setPercentChange30d(usd.getPercent_change_30d());
        tokenPrice.setPercentChange60d(usd.getPercent_change_60d());
        tokenPrice.setMarketCap(usd.getMarket_cap());
        tokenPrice.setMarketCapDominance(usd.getMarket_cap_dominance());
        tokenPrice.setFullyDilutedMarketCap(usd.getFully_diluted_market_cap());
        return tokenPrice;
    }

}

