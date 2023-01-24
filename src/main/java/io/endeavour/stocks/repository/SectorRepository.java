package io.endeavour.stocks.repository;

import io.endeavour.stocks.entity.SectorEntity;
import io.endeavour.stocks.entity.StockFundamentalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<SectorEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM ENDEAVOUR.SECTOR_LOOKUP sl")
    List<SectorEntity> getAll();

}
