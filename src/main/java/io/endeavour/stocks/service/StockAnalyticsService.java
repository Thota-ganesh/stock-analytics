package io.endeavour.stocks.service;

import io.endeavour.stocks.StockException;
import io.endeavour.stocks.dao.LooksupDao;
import io.endeavour.stocks.dao.PriceHistoryDao;
import io.endeavour.stocks.dao.StockFundamentalsDao;
import io.endeavour.stocks.entity.StockFundamentalsEntity;
import io.endeavour.stocks.entity.StockPriceHistoryEntity;
import io.endeavour.stocks.entity.StockPriceHistoryPK;
import io.endeavour.stocks.repository.StockFundamentalsRepository;
import io.endeavour.stocks.repository.StockPriceHistoryRepository;
import io.endeavour.stocks.vo.SectorLookup;
import io.endeavour.stocks.vo.StockFundamentalsHistory;
import io.endeavour.stocks.vo.StocksPriceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockAnalyticsService {
    private final PriceHistoryDao priceHistoryDao;
    private final LooksupDao looksupDao;
    private final StockFundamentalsRepository stockFundamentalsRepository;
    private final StockFundamentalsDao stockFundamentalsDao;
    private final StockPriceHistoryRepository stockPriceHistoryRepository;

    @Autowired
    public StockAnalyticsService(PriceHistoryDao priceHistoryDao,
                                 LooksupDao looksupDao,
                                 StockFundamentalsRepository stockFundamentalsRepository,
                                 StockFundamentalsDao stockFundamentalsDao,
                                 StockPriceHistoryRepository stockPriceHistoryRepository) {
        this.priceHistoryDao = priceHistoryDao;
        this.looksupDao = looksupDao;
        this.stockFundamentalsRepository = stockFundamentalsRepository;
        this.stockFundamentalsDao = stockFundamentalsDao;
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
    }

    public List<StocksPriceHistory> getPriceHistory (List<String> tickerSymbol, LocalDate fromDate, LocalDate toDate){
        return priceHistoryDao.getPriceHistory(tickerSymbol, fromDate, toDate);
    }

    public List<SectorLookup> getSectorName (int sectorId){
        return looksupDao.getSectorName(sectorId);
    }

    public List<StockFundamentalsEntity> getStocks(){
        return stockFundamentalsRepository.findAll();
//        return stockFundamentalsRepository.getAll();
//        return stockFundamentalsRepository.findAllByMarketCapNotNull();
//        return stockFundamentalsRepository.getAllNativeQuery();
    }

    // service class
    public List<StockFundamentalsEntity> getTopByMarketCap(int num) {
        return stockFundamentalsRepository.getTopByMarketCap(num);
//        return stockFundamentalsDao.topByMarketCap(num);
//        return stockFundamentalsDao.topByMarketCapOOP(num);
    }

    public Optional<StockPriceHistoryEntity> getPriceHistoryByKey(String tickerSymbol, LocalDate tradingDate){
        StockPriceHistoryPK stockPriceHistoryPK = new StockPriceHistoryPK();
        stockPriceHistoryPK.setTickerSymbol(tickerSymbol);
        stockPriceHistoryPK.setTradingDate(tradingDate);
        return stockPriceHistoryRepository.findById(stockPriceHistoryPK);
    }
    public StockFundamentalsHistory getStockFundamentalsHistory(String tickerSymbol, LocalDate fromDate, LocalDate toDate){
        StockFundamentalsEntity stockFundamentalsEntity = stockFundamentalsRepository.findById(tickerSymbol).
                orElseThrow(() -> new StockException("Could not find ticker"));
        List<StocksPriceHistory> priceHistories = priceHistoryDao.getPriceHistory(List.of(tickerSymbol), fromDate, toDate);
        StockFundamentalsHistory stockFundamentalsHistory = new StockFundamentalsHistory();
        stockFundamentalsHistory.setMarketCap(stockFundamentalsEntity.getMarketCap());
        stockFundamentalsHistory.setCurrentRatio(stockFundamentalsEntity.getCurrentRatio());
        stockFundamentalsHistory.setTradingHistory(priceHistories);
        return stockFundamentalsHistory;
    }
}
