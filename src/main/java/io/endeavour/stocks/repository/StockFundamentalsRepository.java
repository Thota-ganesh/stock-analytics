package io.endeavour.stocks.repository;

import io.endeavour.stocks.entity.StockFundamentalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockFundamentalsRepository extends JpaRepository<StockFundamentalsEntity, String> {

    //Using SpringData DSL
    List<StockFundamentalsEntity> findAllByMarketCapNotNull();

    // Using JPQL
    @Query(value = "select sf from StockFundamentalsEntity sf where sf.marketCap is not null")
    List<StockFundamentalsEntity> getAll();

    // Using pure SQL
    @Query(nativeQuery = true, value = "SELECT * FROM ENDEAVOUR.STOCK_FUNDAMENTALS WHERE MARKET_CAP IS NOT NULL")
    List<StockFundamentalsEntity> getAllNativeQuery();

    //Using NativeQuery by Limit
    @Query(nativeQuery = true, value = """
            SELECT * FROM ENDEAVOUR.STOCK_FUNDAMENTALS 
            WHERE MARKET_CAP IS NOT NULL
            ORDER BY MARKET_CAP DESC FETCH FIRST :num ROWS ONLY
            """)
    List<StockFundamentalsEntity> getTopByMarketCap(@Param("num") int num);

}
