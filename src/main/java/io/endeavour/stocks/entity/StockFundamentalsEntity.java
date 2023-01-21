package io.endeavour.stocks.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "STOCK_FUNDAMENTALS", schema = "ENDEAVOUR")
public class StockFundamentalsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKER_SYMBOL")
    private String tickerSymbol;
    @Column(name = "SECTOR_ID")
    private Integer sectorId;
    @Column(name = "SUBSECTOR_ID")
    private Integer subSectorId;
    @Column(name = "MARKET_CAP")
    private BigDecimal marketCap;
    @Column(name = "CURRENT_RATIO")
    private BigDecimal currentRatio;

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    public Integer getSubSectorId() {
        return subSectorId;
    }

    public void setSubSectorId(Integer subSectorId) {
        this.subSectorId = subSectorId;
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
}
