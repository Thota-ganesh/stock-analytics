package io.endeavour.stocks.service;

import io.endeavour.stocks.dao.LooksupDao;
import io.endeavour.stocks.dao.PriceHistoryDao;
import io.endeavour.stocks.vo.SectorLookup;
import io.endeavour.stocks.vo.StocksPriceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockAnalyticsService {
    private final PriceHistoryDao priceHistoryDao;
    private final LooksupDao looksupDao;
    @Autowired
    public StockAnalyticsService(PriceHistoryDao priceHistoryDao, LooksupDao looksupDao) {
        this.priceHistoryDao = priceHistoryDao;
        this.looksupDao = looksupDao;
    }

    public List<StocksPriceHistory> getPriceHistory (List<String> tickerSymbol, LocalDate fromDate, LocalDate toDate){
        return priceHistoryDao.getPriceHistory(tickerSymbol, fromDate, toDate);
    }

    public List<SectorLookup> getSectorName (int sectorId){
        return looksupDao.getSectorName(sectorId);
    }
}
