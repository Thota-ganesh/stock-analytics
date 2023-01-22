package io.endeavour.stocks.vo;

import java.math.BigDecimal;
import java.util.List;

public class StockFundamentalsHistory {

    private BigDecimal marketCap;
    private BigDecimal currentRatio;
    private List<StocksPriceHistory> tradingHistory;

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

    public List<StocksPriceHistory> getTradingHistory() {
        return tradingHistory;
    }

    public void setTradingHistory(List<StocksPriceHistory> tradingHistory) {
        this.tradingHistory = tradingHistory;
    }
}
