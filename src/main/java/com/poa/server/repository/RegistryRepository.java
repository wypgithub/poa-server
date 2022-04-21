package com.poa.server.repository;

import com.poa.server.entity.PoaRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RegistryRepository extends JpaRepository<PoaRegistry, String> {

    @Query("select d from PoaRegistry r,PoaDocument d,PoaProfile p where r.documentId = d.id" +
            " and d.profileId = p.id and p.email=:email and d.type=:type ")
    List<PoaRegistry> findByTypeAndName(@Param("email")String email, @Param("type") String type);
}
