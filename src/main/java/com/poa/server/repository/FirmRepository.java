package com.poa.server.repository;

import com.poa.server.entity.Firm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmRepository extends JpaRepository<Firm, String> {

    @Query("SELECT f FROM Firm f, User u " +
            "WHERE f.id = u.firmId AND u.id = ?1")
    Firm findByUserId(String userId);
}
