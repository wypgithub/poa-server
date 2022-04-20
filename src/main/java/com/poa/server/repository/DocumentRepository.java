package com.poa.server.repository;

import com.poa.server.entity.PoaDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Repository
public interface DocumentRepository extends JpaRepository<PoaDocument, String> {

}
