package com.poa.server.repository;

import com.poa.server.entity.PoaRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RegistryRepository extends JpaRepository<PoaRegistry, String> {

    @Query("select d from PoaRegistry r,PoaDocument d where d.id = r.documentId and d.type=:type and d.name=:name")
    List<PoaRegistry> findByTypeAndName(@Param("type") String type, @Param("name")String name);
}
