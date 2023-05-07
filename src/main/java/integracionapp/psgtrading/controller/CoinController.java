package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.coins.response.CoinDTO;
import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.TokenPrice;
import integracionapp.psgtrading.repository.SymbolRepository;
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
public class CoinController {


    private final NewStockService newStockService;
    private final SymbolRepository symbolRepository;

    @GetMapping("/price/{symbol}")
    public ResponseEntity<GenericDTO<CoinDTO>> getCoinPrice(@PathVariable("symbol") String symbol) {
        Symbol symbol1 = symbolRepository.findBySymbol(symbol);
        if (symbol1 == null){
            return ResponseEntity.status(400).body(GenericDTO.error("Token not found."));
        }


        TokenPrice price = newStockService.getCoinPrice(symbol);
        CoinDTO coinDTO = new CoinDTO();
        coinDTO.setTokenPrice(price);
        coinDTO.setSymbol(symbol1.getSymbol());
        coinDTO.setName(symbol1.getName());
        return new ResponseEntity<>(GenericDTO.success(coinDTO), HttpStatus.OK);

    }

    @GetMapping("/price")
    public ResponseEntity<GenericDTO<List<CoinDTO>>> getCoinsPrices() {
        List<Symbol> tokens = symbolRepository.findByIsTokenTrue();
        List<CoinDTO> prices = new ArrayList<>();
        for(Symbol symbol: tokens ){
            TokenPrice price = newStockService.getCoinPrice(symbol.getSymbol());
            CoinDTO coinDTO = new CoinDTO();
            coinDTO.setTokenPrice(price);
            coinDTO.setSymbol(symbol.getSymbol());
            coinDTO.setName(symbol.getName());
            prices.add(coinDTO);
        }
        return new ResponseEntity<>(GenericDTO.success(prices), HttpStatus.OK);

    }

    @GetMapping("")
    public GenericDTO<List<Symbol>> getAllTokens() {

        List<Symbol> tokens = symbolRepository.findAll();

        return GenericDTO.success(tokens);

    }


}
