package com.poa.server.repository;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Service
public class SqlQueryDao {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Map<String, Object>> findAll(String querySql, Integer pageNum, Integer pageSize) {
        NativeQueryImpl query = entityManager.createNativeQuery(querySql).unwrap(NativeQueryImpl.class);

        query.setFirstResult(pageNum * pageSize);
        query.setMaxResults(pageSize);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> resultList = query.getResultList();

        String totalSql = String.format("select count(*) from (%s)sub", querySql);
        Query queryCount = this.entityManager.createNativeQuery(totalSql);
        Long pageTotal = Long.valueOf(queryCount.getSingleResult().toString());

        return new PageImpl<>(resultList, PageRequest.of(pageNum, pageSize), pageTotal);
    }

    public List<Map<String, Object>> findList(String querySql) {
        NativeQueryImpl query = entityManager.createNativeQuery(querySql).unwrap(NativeQueryImpl.class);

        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.getResultList();
    }
}
