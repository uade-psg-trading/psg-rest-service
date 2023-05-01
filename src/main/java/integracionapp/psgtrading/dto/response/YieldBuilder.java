package integracionapp.psgtrading.dto.response;

import integracionapp.psgtrading.model.Balance;
import integracionapp.psgtrading.model.TokenPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class YieldBuilder {

    private Balance balance;
    private TokenPrice coin;

    public Yield build(){
        Yield yield = new Yield();

        yield.setPrice(this.coin.getPrice());
        yield.setAmount(this.balance.getAmount());
        yield.setTotal(this.coin.getPrice() * this.balance.getAmount());
        yield.setPercent_change_24h(this.coin.getPercentChange24h());
        yield.setSymbol(this.balance.getSymbol());
        yield.setYield(this.coin.getVolumeChange24h());

        return yield;
    }

}
