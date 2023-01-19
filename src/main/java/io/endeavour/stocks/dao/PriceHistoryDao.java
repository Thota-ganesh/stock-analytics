package io.endeavour.stocks.dao;

import io.endeavour.stocks.vo.StocksPriceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class PriceHistoryDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    public PriceHistoryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<StocksPriceHistory> getPriceHistory(List<String> tickerSymbols, LocalDate fromDate, LocalDate toDate) {
        String priceHistoryQuery = """
                            SELECT TICKER_SYMBOL, OPEN_PRICE, CLOSE_PRICE, HIGH_PRICE, LOW_PRICE, VOLUME, TRADING_DATE
                            FROM ENDEAVOUR.STOCKS_PRICE_HISTORY
                            WHERE TICKER_SYMBOL IN (:tickerSymbol)
                            AND TRADING_DATE BETWEEN :fromDate and :toDate
                            """;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("tickerSymbol", tickerSymbols);
        mapSqlParameterSource.addValue("fromDate", fromDate);
        mapSqlParameterSource.addValue("toDate", toDate);
        List<StocksPriceHistory> stocksPriceHistories = namedParameterJdbcTemplate.query(priceHistoryQuery, mapSqlParameterSource,
                (resultSet, rowNumber) -> {
            String tickerSymbol = resultSet.getString("TICKER_SYMBOL");
            BigDecimal openPrice = resultSet.getBigDecimal("OPEN_PRICE");
            BigDecimal closePrice = resultSet.getBigDecimal("CLOSE_PRICE");
            BigDecimal highPrice = resultSet.getBigDecimal("HIGH_PRICE");
            BigDecimal lowPrice = resultSet.getBigDecimal("LOW_PRICE");
            BigDecimal volume = resultSet.getBigDecimal("VOLUME");
            LocalDate tradingDate = resultSet.getDate("TRADING_DATE").toLocalDate();
            StocksPriceHistory stocksPriceHistoryRow = new StocksPriceHistory();
            stocksPriceHistoryRow.setTickerSymbol(tickerSymbol);
            stocksPriceHistoryRow.setOpenPrice(openPrice);
            stocksPriceHistoryRow.setClosePrice(closePrice);
            stocksPriceHistoryRow.setHighPrice(highPrice);
            stocksPriceHistoryRow.setLowPrice(lowPrice);
            stocksPriceHistoryRow.setVolume(volume);
            stocksPriceHistoryRow.setTradingDate(tradingDate);
            return stocksPriceHistoryRow;
        });
        return stocksPriceHistories;
    }
}
