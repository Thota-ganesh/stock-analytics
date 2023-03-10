package io.endeavour.stocks.repository;

import io.endeavour.stocks.entity.PersonEntity;
import io.endeavour.stocks.entity.StockFundamentalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
    @Query(nativeQuery = true, value = """
            SELECT * FROM ENDEAVOUR_TEST_AREA.PERSON p  
            WHERE LOWER(FIRST_NAME) LIKE %:query% 
            OR  
            LOWER(LAST_NAME) LIKE %:query%
            """)
    List<PersonEntity> getPersonDetails(@Param("query") String query);

}
