package com.poa.server.repository;

import com.poa.server.entity.Firm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmRepository extends JpaRepository<Firm, String> {

    @Query("SELECT f FROM Firm f, UserFirm uf " +
            "WHERE f.id = uf.firmId AND uf.userId = ?1")
    Firm findByUserId(String userId);
}
