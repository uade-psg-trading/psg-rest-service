package integracionapp.psgtrading.dto.coins;

import lombok.Data;

import java.util.List;

@Data
public class Coin {

    private int id;
    private String name;
    private String symbol;
    private String slug;
    private int num_market_pairs;
    private String date_added;
    private List<String> tags;
    private double max_supply;
    private double circulating_supply;
    private double total_supply;
    private int is_active;
    private boolean infinite_supply;
    private int cmc_rank;
    private int is_fiat;
    private double self_reported_circulating_supply;
    private double self_reported_market_cap;
    private Double tvl_ratio;
    private String last_updated;
    private Quote quote;

}
