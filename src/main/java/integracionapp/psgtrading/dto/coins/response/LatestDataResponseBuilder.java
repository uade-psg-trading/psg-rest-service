package integracionapp.psgtrading.dto.coins.response;

import integracionapp.psgtrading.dto.coins.Coin;
import integracionapp.psgtrading.dto.coins.latest.LatestDataResponse;
import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class LatestDataResponseBuilder {

    private LatestDataResponse response;
    private String symbol;

    public CoinDTO build(){
        CoinDTO coinDto = new CoinDTO();
        Coin coin = getCoin();

        coinDto.setId(coin.getId());
        coinDto.setName(coin.getName());
        coinDto.setPrice(coin.getQuote().getUSD().getPrice());
        coinDto.setPercent_change_24h(coin.getQuote().getUSD().getPercent_change_24h());
        coinDto.setSymbol(this.symbol);
        coinDto.setVolume_change_24h(coin.getQuote().getUSD().getVolume_change_24h());

        return coinDto;
    }

    private Coin getCoin() {
        Coin coin ;

        switch (this.symbol){
            case "PSG": {
                coin = this.response.getData().getPSG();
                break;
            }
            case "BAR": {
                coin = this.response.getData().getBAR();
                break;
            }
            case "CITY": {
                coin = this.response.getData().getCITY();
                break;
            }
            case "LAZIO": {
                coin = this.response.getData().getLAZIO();
                break;
            }
            case "PORTO": {
                coin = this.response.getData().getPORTO();
                break;
            }
            case "SANTOS": {
                coin = this.response.getData().getSANTOS();
                break;
            }
            case "AFC": {
                coin = this.response.getData().getAFC();
                break;
            }
            case "ACM": {
                coin = this.response.getData().getACM();
                break;
            }
            case "JUV": {
                coin = this.response.getData().getJUV();
                break;
            }
            case "NAP": {
                coin = this.response.getData().getNAP();
                break;
            }
            default: {
                throw new CustomRuntimeException(ErrorCode.GENERAL_ERROR, "Coin not found");
            }
        }
        return coin;
    }
}
