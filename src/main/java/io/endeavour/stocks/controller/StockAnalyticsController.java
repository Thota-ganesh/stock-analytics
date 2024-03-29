package io.endeavour.stocks.controller;

import io.endeavour.stocks.entity.StockFundamentalsEntity;
import io.endeavour.stocks.entity.StockPriceHistoryEntity;
import io.endeavour.stocks.service.StockAnalyticsService;
import io.endeavour.stocks.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/stockFundamentals")
    public List<StockFundamentalsEntity> getStocks (){
        return stockAnalyticsService.getStocks();
    }

    // NativeQuery by Limit
    @GetMapping("/stock-fundamentals/top/{num}/by-market-cap")
    public List<StockFundamentalsEntity> getTopByMarketCap(@PathVariable("num") int num) {
        return stockAnalyticsService.getTopByMarketCap(num);
    }

    @GetMapping("/price-history/{tickerSymbol}/{tradingDate}")
    public ResponseEntity<StockPriceHistoryEntity> priceHistoryByKey(@PathVariable("tickerSymbol") String tickerSymbol,
                                                                     @PathVariable("tradingDate")
                                                                     @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                     LocalDate tradingDate){
        return ResponseEntity.of(stockAnalyticsService.getPriceHistoryByKey(tickerSymbol,tradingDate));
    }

    @GetMapping("/stock-fundamentals/{tickerSymbol}/{fromDate}/{toDate}")
    public StockFundamentalsHistory getStockFundamentalsHistory (@PathVariable("tickerSymbol") String tickerSymbol,
                                                                 @PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                                 @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate){
        return stockAnalyticsService.getStockFundamentalsHistory(tickerSymbol, fromDate, toDate);
    }

    @GetMapping("/stock-fundamentals/top/{num}/{fromDate}/{toDate}")
    public List<StockFundamentalsEntity> getTopStocks(@PathVariable("num") int num,
                                                      @PathVariable("fromDate")
                                                      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                      @PathVariable("toDate")
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        return stockAnalyticsService.getTopStocks(num, fromDate, toDate);
    }

    @GetMapping("/top-stocks/top/{num}/{fromDate}/{toDate}")
    public  List<SectorLookupHistory> getTopNStocks(@PathVariable("num") int num,
                                                    @PathVariable("fromDate")
                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                    @PathVariable("toDate")
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        return stockAnalyticsService.getSectorLookupHistory(num, fromDate, toDate);
    }

    @GetMapping("/sub-sec-stocks/top/{num}")
    public  List<TopSubSectors> topSubSecStocks(@PathVariable("num") int num) {
        return stockAnalyticsService.topSubSectors(num);
    }

}
