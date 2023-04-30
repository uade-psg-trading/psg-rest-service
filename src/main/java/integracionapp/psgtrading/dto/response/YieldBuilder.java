package integracionapp.psgtrading.dto.response;

import integracionapp.psgtrading.dto.coins.response.CoinDTO;
import integracionapp.psgtrading.model.Balance;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class YieldBuilder {

    private Balance balance;
    private CoinDTO coin;

    public Yield build(){
        Yield yield = new Yield();

        yield.setPrice(this.coin.getPrice());
        yield.setAmount(this.balance.getAmount());
        yield.setTotal(this.coin.getPrice() * this.balance.getAmount());
        yield.setPercent_change_24h(this.coin.getPercent_change_24h());
        yield.setSymbol(this.balance.getSymbol());
        yield.setYield(this.coin.getVolume_change_24h());

        return yield;
    }

}
