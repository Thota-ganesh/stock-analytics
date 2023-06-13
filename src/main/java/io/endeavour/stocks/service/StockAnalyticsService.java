package io.endeavour.stocks.service;

import io.endeavour.stocks.StockException;
import io.endeavour.stocks.dao.LooksupDao;
import io.endeavour.stocks.dao.PriceHistoryDao;
import io.endeavour.stocks.dao.StockFundamentalsDao;
import io.endeavour.stocks.entity.*;
import io.endeavour.stocks.remote.StockCalculationsClient;
import io.endeavour.stocks.remote.vo.TickerCumulativeReturn;
import io.endeavour.stocks.remote.vo.TickerList;
import io.endeavour.stocks.repository.SectorRepository;
import io.endeavour.stocks.repository.StockFundamentalsRepository;
import io.endeavour.stocks.repository.StockPriceHistoryRepository;
import io.endeavour.stocks.repository.SubSectorRepository;
import io.endeavour.stocks.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockAnalyticsService {
    private final PriceHistoryDao priceHistoryDao;
    private final LooksupDao looksupDao;
    private final StockFundamentalsRepository stockFundamentalsRepository;
    private final StockFundamentalsDao stockFundamentalsDao;
    private final StockPriceHistoryRepository stockPriceHistoryRepository;
    private final StockCalculationsClient stockCalculationsClient;
    private final SectorRepository sectorRepository;
    private final SubSectorRepository subSectorRepository;

    @Autowired
    public StockAnalyticsService(PriceHistoryDao priceHistoryDao,
                                 LooksupDao looksupDao,
                                 StockFundamentalsRepository stockFundamentalsRepository,
                                 StockFundamentalsDao stockFundamentalsDao,
                                 StockPriceHistoryRepository stockPriceHistoryRepository,
                                 StockCalculationsClient stockCalculationsClient, SectorRepository sectorRepository, SubSectorRepository subSectorRepository) {
        this.priceHistoryDao = priceHistoryDao;
        this.looksupDao = looksupDao;
        this.stockFundamentalsRepository = stockFundamentalsRepository;
        this.stockFundamentalsDao = stockFundamentalsDao;
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
        this.stockCalculationsClient = stockCalculationsClient;
        this.sectorRepository = sectorRepository;
        this.subSectorRepository = subSectorRepository;
    }

    public List<StocksPriceHistory> getPriceHistory(List<String> tickerSymbol, LocalDate fromDate, LocalDate toDate) {
        return priceHistoryDao.getPriceHistory(tickerSymbol, fromDate, toDate);
    }

    public List<SectorLookup> getSectorName(int sectorId) {
        return looksupDao.getSectorName(sectorId);
    }

    public List<StockFundamentalsEntity> getStocks() {
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

    public Optional<StockPriceHistoryEntity> getPriceHistoryByKey(String tickerSymbol, LocalDate tradingDate) {
        StockPriceHistoryPK stockPriceHistoryPK = new StockPriceHistoryPK();
        stockPriceHistoryPK.setTickerSymbol(tickerSymbol);
        stockPriceHistoryPK.setTradingDate(tradingDate);
        return stockPriceHistoryRepository.findById(stockPriceHistoryPK);
    }

    public StockFundamentalsHistory getStockFundamentalsHistory(String tickerSymbol, LocalDate fromDate, LocalDate toDate) {
        StockFundamentalsEntity stockFundamentalsEntity = stockFundamentalsRepository.findById(tickerSymbol).
                orElseThrow(() -> new StockException("Could not find ticker"));
        List<StocksPriceHistory> priceHistories = priceHistoryDao.getPriceHistory(List.of(tickerSymbol), fromDate, toDate);
        StockFundamentalsHistory stockFundamentalsHistory = new StockFundamentalsHistory();
        stockFundamentalsHistory.setMarketCap(stockFundamentalsEntity.getMarketCap());
        stockFundamentalsHistory.setCurrentRatio(stockFundamentalsEntity.getCurrentRatio());
        stockFundamentalsHistory.setTradingHistory(priceHistories);
        return stockFundamentalsHistory;
    }

    public List<StockFundamentalsEntity> getTopStocks(int num, LocalDate fromDate, LocalDate toDate) {
        List<StockFundamentalsEntity> stockFundamentals = stockFundamentalsRepository.getAll();
        List<String> tickersSymbols = stockFundamentals.stream()
                .map(StockFundamentalsEntity::getTickerSymbol)
                .collect(Collectors.toList());
        TickerList tickerList = new TickerList();
        tickerList.setTickers(tickersSymbols);
        List<TickerCumulativeReturn> cumulativeReturns =
                stockCalculationsClient.getCumulativeReturns(fromDate, toDate, tickerList);
        //populate cumulative return in the stockFundamentals List
        //return the top N by highest cumulative return
        Map<String, BigDecimal> cumulativeReturnMap = cumulativeReturns.stream()
                .filter(tickerCumulativeReturn -> tickerCumulativeReturn.getCumulativeReturn() != null)
                .collect(Collectors.toMap(TickerCumulativeReturn::getTickerSymbol,
                        TickerCumulativeReturn::getCumulativeReturn));
        stockFundamentals.forEach(stockFundamentalsEntity -> {
            String tickerSymbol = stockFundamentalsEntity.getTickerSymbol();
            BigDecimal cumulativeReturn = cumulativeReturnMap.get(tickerSymbol);
            stockFundamentalsEntity.setCumulativeReturn(cumulativeReturn);
//            stockFundamentalsEntity.setCumulativeReturn(
//                    cumulativeReturnMap.get(
//                            stockFundamentalsEntity.getTickerSymbol()));
        });
        return stockFundamentals.stream()
                .filter(stockFundamentalsEntity -> stockFundamentalsEntity.getCumulativeReturn() != null)
                .sorted(Comparator.comparing(StockFundamentalsEntity::getCumulativeReturn).reversed())
                .limit(num)
                .collect(Collectors.toList());

//        return stockFundamentals;
    }

    public List<SectorLookupHistory> getSectorLookupHistory(int num, LocalDate fromDate, LocalDate toDate) {
        List<StockFundamentalsEntity> stockFundamentals = stockFundamentalsRepository.getAll();
        List<SectorEntity> sectorEntities = sectorRepository.findAll();
        List<SectorLookupHistory> sectorLookupHistoryList = new ArrayList<>();

        List<String> tickersSymbols = stockFundamentals.stream()
                .map(StockFundamentalsEntity::getTickerSymbol)
                .collect(Collectors.toList());
        TickerList tickerList = new TickerList();
        tickerList.setTickers(tickersSymbols);
        List<TickerCumulativeReturn> cumulativeReturns =
                stockCalculationsClient.getCumulativeReturns(fromDate, toDate, tickerList);
        //populate cumulative return in the stockFundamentals List
        //return the top N by highest cumulative return
        Map<String, BigDecimal> cumulativeReturnMap = cumulativeReturns.stream()
                .filter(tickerCumulativeReturn -> tickerCumulativeReturn.getCumulativeReturn() != null)
                .collect(Collectors.toMap(TickerCumulativeReturn::getTickerSymbol,
                        TickerCumulativeReturn::getCumulativeReturn));
        stockFundamentals.forEach(stockFundamentalsEntity -> {
            String tickerSymbol = stockFundamentalsEntity.getTickerSymbol();
            BigDecimal cumulativeReturn = cumulativeReturnMap.get(tickerSymbol);
            stockFundamentalsEntity.setCumulativeReturn(cumulativeReturn);
        });
        Map<Integer, String> sectorMap = sectorEntities.stream()
                .collect(Collectors.toMap(SectorEntity::getSectorId,
                        SectorEntity::getSectorName));
        Map<Integer, List<StockFundamentalsEntity>> stockFundamentalsMap = stockFundamentals.stream()
                .collect(Collectors.groupingBy(StockFundamentalsEntity::getSectorId));
        sectorMap.forEach((id, name) -> {
            SectorLookupHistory sectorLookupHistory = new SectorLookupHistory();
            sectorLookupHistory.setSectorId(id);
            sectorLookupHistory.setSectorName(name);
            List<StockFundamentalsEntity> fundamentalsEntities = stockFundamentalsMap.get(id).stream()
                    .filter(stockFundamentalsEntity -> stockFundamentalsEntity.getCumulativeReturn() != null)
                    .sorted(Comparator.comparing(StockFundamentalsEntity::getCumulativeReturn).reversed())
                    .limit(num)
                    .collect(Collectors.toList());
            sectorLookupHistory.setTopStocks(fundamentalsEntities);
            sectorLookupHistoryList.add(sectorLookupHistory);
        });
        return sectorLookupHistoryList;
    }

    public List<SectorEntity> sectorEntityList(){
        return sectorRepository.findAll();
    }
    public List<SubSectorEntity> subSectorEntityList(){
        return subSectorRepository.findAll();
    }
    public List<StockFundamentalsEntity> stockFundamentalsEntityList(){
        return stockFundamentalsRepository.findAllByMarketCapNotNull();
    }

    public List<TopSubSectors> topSubSectors (int num){
        List<TopSubSectors> topSubSectors = new ArrayList<>();
        List<StockFundamentalsEntity> fundamentalsEntities = stockFundamentalsEntityList();
        Map<Integer, String> sectorList = sectorEntityList().stream()
                .collect(Collectors.toMap(SectorEntity::getSectorId, SectorEntity::getSectorName));
        Map<Integer, String> subSectorList = subSectorEntityList().stream()
                .collect(Collectors.toMap(SubSectorEntity::getSubSectorId, SubSectorEntity::getSubSectorName));
        Map<Integer, List<StockFundamentalsEntity>> fundamentalsBySecId = stockFundamentalsEntityList().stream()
                .collect(Collectors.groupingBy(StockFundamentalsEntity::getSectorId));
        fundamentalsBySecId.forEach((sectorId,stocksBySectorId)->{
            TopSubSectors topSubSec = new TopSubSectors();
            topSubSec.setSectorName(sectorList.get(sectorId));
            Map<Integer, List<StockFundamentalsEntity>> fundamentalBySubSec = stocksBySectorId.stream()
                    .collect(Collectors.groupingBy(StockFundamentalsEntity::getSubSectorId));
            List<TopSubSectorsByAvgMarketCap> topSubSectorsByAvgMarketCaps = new ArrayList<>();
            fundamentalBySubSec.forEach((subSecId, stocksBySubSecId)->{
                TopSubSectorsByAvgMarketCap topSubSectorsByAvgMarketCap = new TopSubSectorsByAvgMarketCap();
                topSubSectorsByAvgMarketCap.setSubSectorName(subSectorList.get(subSecId));
                List<BigDecimal> marketCap = stocksBySubSecId.stream()
                        .map(StockFundamentalsEntity::getMarketCap)
                        .collect(Collectors.toList());
                BigDecimal sum = BigDecimal.ZERO;
                BigDecimal avg = BigDecimal.ZERO;
                for (BigDecimal bigDecimal : marketCap) {
                    sum = sum.add(bigDecimal);
                }
                avg = sum.divide(new BigDecimal(marketCap.size()),3, RoundingMode.HALF_EVEN);
                topSubSectorsByAvgMarketCap.setAvgMarketCap(avg);
                topSubSectorsByAvgMarketCaps.add(topSubSectorsByAvgMarketCap);
            });
            List<TopSubSectorsByAvgMarketCap> subSectorsByAvgMarketCaps = topSubSectorsByAvgMarketCaps.stream()
                    .sorted(Comparator.comparing(TopSubSectorsByAvgMarketCap::getAvgMarketCap))
                    .limit(5)
                    .collect(Collectors.toList());
            topSubSec.setTopSubSectorsByAvgMarketCaps(subSectorsByAvgMarketCaps);
            topSubSectors.add(topSubSec);
        });
        return topSubSectors;

    }

}
