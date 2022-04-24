package com.poa.server.repository;

import com.poa.server.entity.PoaShared;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharedRepository extends JpaRepository<PoaShared, String> {

    List<PoaShared> findByEmailAndDocumentId(String email, String documentId);


}
