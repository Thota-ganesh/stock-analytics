package io.endeavour.stocks.remote;

import io.endeavour.stocks.config.FeignConfiguration;
import io.endeavour.stocks.remote.vo.TickerCumulativeReturn;
import io.endeavour.stocks.remote.vo.TickerList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "stockCalculationsClient", url = "${client.stock-calculations.url}",
configuration = FeignConfiguration.class)
public interface StockCalculationsClient {
    @PostMapping("/calculate/cumulative-return/{fromDate}/{toDate}")
    List<TickerCumulativeReturn> getCumulativeReturns(
            @PathVariable("fromDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate fromDate,
            @PathVariable("toDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate toDate,
            @RequestBody TickerList tickerList);
}
