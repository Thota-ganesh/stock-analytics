package io.endeavour.stocks.repository;

import io.endeavour.stocks.entity.StockFundamentalsEntity;
import io.endeavour.stocks.entity.StockPriceHistoryEntity;
import io.endeavour.stocks.entity.StockPriceHistoryPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceHistoryRepository extends JpaRepository<StockPriceHistoryEntity, StockPriceHistoryPK> {
}
