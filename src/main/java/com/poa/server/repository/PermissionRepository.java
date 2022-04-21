package com.poa.server.repository;


import com.poa.server.entity.PoaPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PoaPermission, String> {
}
