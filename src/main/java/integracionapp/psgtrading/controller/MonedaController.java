package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.coinMarket.response.CoinDTO;
import integracionapp.psgtrading.dto.response.Yield;
import integracionapp.psgtrading.model.Transaction;
import integracionapp.psgtrading.service.NewStockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/coin")
public class MonedaController {


    private final NewStockService newStockService;

    @GetMapping("/{symbol}")
    public GenericDTO<CoinDTO> getCoin(@PathVariable("symbol") String symbol) {

        return GenericDTO.success(newStockService.getCoinPrice(symbol));

    }

}
