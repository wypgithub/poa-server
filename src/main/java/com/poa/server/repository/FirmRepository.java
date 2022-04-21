package com.poa.server.repository;

import com.poa.server.entity.PoaFirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmRepository extends JpaRepository<PoaFirm, String> {

}
