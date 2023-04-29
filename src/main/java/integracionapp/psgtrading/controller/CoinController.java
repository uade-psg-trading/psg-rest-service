package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.coins.response.CoinDTO;
import integracionapp.psgtrading.service.NewStockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/coin")
public class CoinController {


    private final NewStockService newStockService;

    @GetMapping("/{symbol}")
    public GenericDTO<CoinDTO> getCoin(@PathVariable("symbol") String symbol) {

        return GenericDTO.success(newStockService.getCoinPrice(symbol));

    }

}
