package integracionapp.psgtrading.dto.coins.latest;


import integracionapp.psgtrading.dto.coins.Coin;
import integracionapp.psgtrading.dto.coins.Status;
import lombok.Data;

import java.util.Map;

@Data
public class LatestDataResponse {
    private Status status;
    private Map<String, Coin> data;


}
