package com.poa.server.repository;

import com.poa.server.entity.PoaDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Repository
public interface DocumentRepository extends JpaRepository<PoaDocument, String> {

    List<PoaDocument> findByProfileId(String profileId);

    @Modifying(clearAutomatically=true)
    @Query(value = "update poa_document set status = status+' Deceased' where status in ('Draft','Final','Signed','Activated') and profile_id=?1", nativeQuery = true)
    void updateDeceasedStatus(String profileId);

    @Modifying(clearAutomatically=true)
    @Query(value = "update poa_document set status = status+' Inactive' where status in ('Draft','Final','Signed','Activated') and profile_id=?1", nativeQuery = true)
    void updateInactiveStatus(String profileId);
}
