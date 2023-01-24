package io.endeavour.stocks.vo;

import io.endeavour.stocks.entity.StockFundamentalsEntity;

import java.util.List;

public class SectorLookupHistory {
    private int sectorId;
    private String sectorName;
    private List<StockFundamentalsEntity> topStocks;

    public int getSectorId() {
        return sectorId;
    }

    public void setSectorId(int sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public List<StockFundamentalsEntity> getTopStocks() {
        return topStocks;
    }

    public void setTopStocks(List<StockFundamentalsEntity> topStocks) {
        this.topStocks = topStocks;
    }
}
