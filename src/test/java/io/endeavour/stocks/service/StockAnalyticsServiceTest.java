package io.endeavour.stocks.service;

import io.endeavour.stocks.UnitTestBase;
import io.endeavour.stocks.entity.StockFundamentalsEntity;
import io.endeavour.stocks.remote.StockCalculationsClient;
import io.endeavour.stocks.remote.vo.TickerCumulativeReturn;
import io.endeavour.stocks.repository.StockFundamentalsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class StockAnalyticsServiceTest extends UnitTestBase {
    @MockBean
    StockFundamentalsRepository stockFundamentalsRepository;

    @MockBean
     StockCalculationsClient stockCalculationsClient;

    @Autowired
    StockAnalyticsService stockAnalyticsService;

    @Test
    void getTopStocks() {
        StockFundamentalsEntity stockFundamentals = new StockFundamentalsEntity();
        stockFundamentals.setTickerSymbol("AAPL");
        stockFundamentals.setMarketCap(new BigDecimal("100"));
        stockFundamentals.setCurrentRatio(new BigDecimal("1.0"));

        List<StockFundamentalsEntity> stockFundamentalsEntities = List.of(stockFundamentals);
        Mockito.when(stockFundamentalsRepository.getAll()).thenReturn(stockFundamentalsEntities);

        TickerCumulativeReturn tickerCumulativeReturn = new TickerCumulativeReturn();
        tickerCumulativeReturn.setTickerSymbol("AAPL");
        tickerCumulativeReturn.setCumulativeReturn(new BigDecimal("0.7"));
        List<TickerCumulativeReturn> tickerCumulativeReturns = List.of(tickerCumulativeReturn);

        Mockito.when(stockCalculationsClient.getCumulativeReturns(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(tickerCumulativeReturns);

        List<StockFundamentalsEntity> topStocks =
                stockAnalyticsService.getTopStocks(5, LocalDate.now().minusMonths(1), LocalDate.now());

        Assertions.assertEquals(1, topStocks.size());
    }
}