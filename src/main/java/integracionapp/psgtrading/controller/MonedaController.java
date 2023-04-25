package integracionapp.psgtrading.controller;

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
@RequestMapping("/coins")
public class MonedaController {


    private final NewStockService newStockService;

    @GetMapping("/{symbol}")
    public ResponseEntity<List<Transaction>> getCoin(@PathVariable("symbol") String symbol) {

        System.out.println(newStockService.getCoinPrice(symbol));

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);

    }

}
