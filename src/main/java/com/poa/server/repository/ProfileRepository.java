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

    @Query(value = "select p.*,(select count(id) from poa_document where profile_id=p.id)files from poa_profile p",
            nativeQuery = true)
    Page<PoaProfile> findProfiles(Pageable pageable);

}
