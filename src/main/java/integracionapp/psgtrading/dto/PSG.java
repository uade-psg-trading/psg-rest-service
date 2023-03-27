package integracionapp.psgtrading.dto;

import lombok.Data;

import java.util.List;

@Data
public class PSG {

    private String symbol;
    private Integer circulating_supply;
    private String last_updated;
    private Integer is_active;
    private Integer total_supply;
    private Integer cmc_rank;
    private String self_reported_circulating_supply;
    private String platform;
    private List<String> tags;
    private String date_added;
    private Quote quote;
    private Integer num_market_pairs;
    private String name;
    private Integer max_supply;
    private Integer id;
    private String self_reported_market_cap;
    private String is_fiat;
    private String slug;

}
