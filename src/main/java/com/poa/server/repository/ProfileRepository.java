package com.poa.server.repository;

import com.poa.server.entity.PoaProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Repository
public interface ProfileRepository extends JpaRepository<PoaProfile, String> {

    List<PoaProfile> findByEmailAndStatus(String email, String status);

    List<PoaProfile> findByEmailAndStatusAndIdNot(String email, String status, String id);

   /* @Query(value = "select p from PoaProfile p where email=?1 and status=?2")
    PoaProfile findByEmailAnd(String email, String status);*/
}
