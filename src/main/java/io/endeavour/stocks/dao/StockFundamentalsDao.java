package io.endeavour.stocks.dao;

import io.endeavour.stocks.entity.StockFundamentalsEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class StockFundamentalsDao {
    private final EntityManager entityManager;

    public StockFundamentalsDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<StockFundamentalsEntity> topByMarketCap(int num) {
        String jpqlQuery = """
                select sf from StockFundamentalsEntity sf 
                where sf.marketCap is not null 
                order by sf.marketCap desc
                """;
        TypedQuery<StockFundamentalsEntity> typedQuery =
                entityManager.createQuery(jpqlQuery, StockFundamentalsEntity.class);
        typedQuery.setMaxResults(num);
        return typedQuery.getResultList();
    }

    public List<StockFundamentalsEntity> topByMarketCapOOP(int num) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockFundamentalsEntity> criteriaQuery = cb.createQuery(StockFundamentalsEntity.class);
        Root<StockFundamentalsEntity> root = criteriaQuery.from(StockFundamentalsEntity.class);
        criteriaQuery.select(root)
                .where(cb.isNotNull(root.get("marketCap")))
                .orderBy(cb.desc(root.get("marketCap")));
        List<StockFundamentalsEntity> resultList = entityManager.createQuery(criteriaQuery)
                .setMaxResults(num)
                .getResultList();
        return resultList;
    }
}
