package io.endeavour.stocks.controller;

import io.endeavour.stocks.service.StockAnalyticsService;
import io.endeavour.stocks.vo.SectorLookup;
import io.endeavour.stocks.vo.StocksPriceHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/analytics")
public class StockAnalyticsController {
    private final StockAnalyticsService stockAnalyticsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StockAnalyticsController.class);
    @Autowired
    public StockAnalyticsController(StockAnalyticsService stockAnalyticsService) {
        this.stockAnalyticsService = stockAnalyticsService;
    }

    @GetMapping("/price-history/{tickerSymbol}/{fromDate}/{toDate}")
    public List<StocksPriceHistory> getPriceHistory(@PathVariable("tickerSymbol") String tickerSymbol,
                                                    @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                    @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate){
        LOGGER.info("API Inputs are {} {} {}", tickerSymbol, fromDate,toDate);
        if (fromDate.isAfter(toDate)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From date can't be after to date");
        }
        return stockAnalyticsService.getPriceHistory(Collections.singletonList(tickerSymbol),fromDate,toDate);
    }

    @PostMapping("/price-history/{fromDate}/{toDate}")
    public List<StocksPriceHistory> getPriceHistory(
            @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            @RequestBody List<String> tickerSymbols){
        return stockAnalyticsService.getPriceHistory(tickerSymbols,fromDate,toDate);

    }

    @GetMapping("/price-history/{tickerSymbol}")
    public StocksPriceHistory getPriceHistoryQueryParam(@PathVariable("tickerSymbol") String tickerSymbol,
                                              @RequestParam (value = "fromDate", required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                              @RequestParam(value = "toDate", required = false)
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        LOGGER.info("API Inputs are {} {} {}", tickerSymbol, fromDate,toDate);
        if (fromDate.isAfter(toDate)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "From date can't be after to date");
        }
        return new StocksPriceHistory();
    }
    @GetMapping("/sectorName/{sectorId}")
    public List<SectorLookup> getSectorName (@PathVariable("sectorId") int sectorId){
        LOGGER.info("API Inputs are {} ", sectorId);
        return stockAnalyticsService.getSectorName(sectorId);
    }

}
