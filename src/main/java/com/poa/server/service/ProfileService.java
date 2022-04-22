package com.poa.server.service;

import com.poa.server.entity.PoaEstateTrustee;
import com.poa.server.entity.PoaProfile;
import com.poa.server.repository.EstateTrusteeRepository;
import com.poa.server.repository.ProfileRepository;
import com.poa.server.util.Constants;
import com.poa.server.util.ResponseMsg;
import com.poa.server.vo.SearchParamVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;


/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EstateTrusteeRepository estateTrusteeRepository;


    public ResponseMsg save(PoaProfile profile) {
        if (StringUtils.isBlank(profile.getId())){
            profile.setStatus(Constants.ProfileStatus.Open);
        }
        String profileId = profileRepository.save(profile).getId();
        if (profile.getEstateTrustee() != null){
            for (PoaEstateTrustee trustee : profile.getEstateTrustee()){
                trustee.setProfileId(profileId);
            }
            estateTrusteeRepository.saveAll(profile.getEstateTrustee());
        }

        return ResponseMsg.ok(profileId);
    }

    public ResponseMsg updateStatus(PoaProfile profile) {
        if (StringUtils.isBlank(profile.getId())){
            return ResponseMsg.error("id of profile is empty");
        }
        profileRepository.save(profile).getId();

        estateTrusteeRepository.saveAll(profile.getEstateTrustee());

        return ResponseMsg.ok(profileRepository.save(profile).getId());
    }

    public ResponseMsg findById(String id) {
        return ResponseMsg.ok(profileRepository.findById(id));
    }

    public ResponseMsg delete(String id) {
        profileRepository.deleteById(id);
        return ResponseMsg.ok();
    }

    public ResponseMsg listAll(SearchParamVO paramVO) {

        if("".equals(paramVO.getDocumentType())){

        }


        Pageable pageable = PageRequest.of(paramVO.getPageNum(), paramVO.getPageNum());
        Specification<PoaProfile> specification = (root, query, cb) ->{
            List<Predicate> predicates = new LinkedList<>();
            if(StringUtils.isNotBlank(paramVO.getFirstName())){
                predicates.add(cb.equal(root.get("firstName"), paramVO.getFirstName()));
            }
            if(StringUtils.isNotBlank(paramVO.getMiddleName())){
                predicates.add(cb.equal(root.get("middleName"), paramVO.getMiddleName()));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return ResponseMsg.ok(profileRepository.findProfiles( pageable));
    }
}
