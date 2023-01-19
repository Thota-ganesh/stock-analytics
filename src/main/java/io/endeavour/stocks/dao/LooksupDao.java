package io.endeavour.stocks.dao;

import io.endeavour.stocks.vo.SectorLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LooksupDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    public LooksupDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<SectorLookup> getSectorName(int sectorId){
        String looksUpQuery = "SELECT SECTOR_ID, SECTOR_NAME FROM ENDEAVOUR.SECTOR_LOOKUP WHERE SECTOR_ID = :SECTOR_ID";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("SECTOR_ID", sectorId);
        List<SectorLookup> query = namedParameterJdbcTemplate.query(looksUpQuery, mapSqlParameterSource, (resultSet, rowNumber) -> {
            //int sectorId = resultSet.getInt("SECTOR_ID");
            String sectorName = resultSet.getString("SECTOR_NAME");
            SectorLookup sectorLookupRow = new SectorLookup();
            sectorLookupRow.setSectorId(sectorId);
            sectorLookupRow.setSectorName(sectorName);
            return sectorLookupRow;

        });

        return query;
    }
}
