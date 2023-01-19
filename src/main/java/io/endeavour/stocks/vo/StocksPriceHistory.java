package io.endeavour.stocks.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StocksPriceHistory {
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    private String tickerSymbol;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    private LocalDate tradingDate;

    public LocalDate getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(LocalDate tradingDate) {
        this.tradingDate = tradingDate;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    private BigDecimal volume;

    @Override
    public String toString() {
        return "StocksPriceHistory{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", lowPrice=" + lowPrice +
                ", highPrice=" + highPrice +
                ", volume=" + volume +
                ", tradingDate" + tradingDate +
                '}';
    }
}
