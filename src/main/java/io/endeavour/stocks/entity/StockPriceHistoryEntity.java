package io.endeavour.stocks.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "STOCKS_PRICE_HISTORY", schema = "ENDEAVOUR")
@IdClass(StockPriceHistoryPK.class)
public class StockPriceHistoryEntity {
    @Id
    @Column(name = "TICKER_SYMBOL")
    private String tickerSymbol;
    @Id
    @Column(name = "TRADING_DATE")
    private LocalDate tradingDate;
    @Column(name = "CLOSE_PRICE")
    private BigDecimal closePrice;
    @Column(name = "VOLUME")
    private Long volume;

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public LocalDate getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(LocalDate tradingDate) {
        this.tradingDate = tradingDate;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
}
