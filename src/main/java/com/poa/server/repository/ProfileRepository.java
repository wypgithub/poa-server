package com.poa.server.repository;

import com.poa.server.entity.PoaProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Repository
public interface ProfileRepository extends JpaRepository<PoaProfile, String> {

}
