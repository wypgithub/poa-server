package com.poa.server.repository;

import com.poa.server.entity.PoaRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistryRepository extends JpaRepository<PoaRegistry, String> {

}
