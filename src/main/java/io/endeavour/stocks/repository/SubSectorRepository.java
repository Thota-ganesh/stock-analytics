package io.endeavour.stocks.repository;

import io.endeavour.stocks.entity.SubSectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubSectorRepository extends JpaRepository<SubSectorEntity, Integer> {
}
