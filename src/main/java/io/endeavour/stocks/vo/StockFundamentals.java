package io.endeavour.stocks.vo;

import java.math.BigDecimal;

public class StockFundamentals implements Comparable<StockFundamentals> {
    private int sectorId;

    public int getSubSectorId() {
        return subSectorId;
    }

    public void setSubSectorId(int subSectorId) {
        this.subSectorId = subSectorId;
    }

    private int subSectorId;

    public int getSectorId() {
        return sectorId;
    }

    public void setSectorId(int sectorId) {
        this.sectorId = sectorId;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public BigDecimal getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(BigDecimal currentRatio) {
        this.currentRatio = currentRatio;
    }

    private String tickerSymbol;
    private BigDecimal marketCap;
    private BigDecimal currentRatio;


    @Override
    public int compareTo(StockFundamentals that) {
        return this.marketCap.compareTo(that.marketCap);
    }

    @Override
    public String toString() {
        return "StockFundamentals{" +
                "sectorId=" + sectorId +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                ", marketCap=" + marketCap +
                ", currentRatio=" + currentRatio +
                ", subSectorId=" + subSectorId +
                '}';
    }
}
