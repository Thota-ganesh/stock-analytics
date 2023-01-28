package io.endeavour.stocks.repository;

import io.endeavour.stocks.entity.AddressEntity;
import io.endeavour.stocks.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {
    @Query(nativeQuery = true, value = """
            SELECT * FROM ENDEAVOUR_TEST_AREA.ADDRESS p  
            WHERE LOWER(LINE_1) LIKE %:query% 
            OR  
            LOWER(LINE_2) LIKE %:query%
            """)
    List<AddressEntity> getAddressDetails(@Param("query") String query);
}
