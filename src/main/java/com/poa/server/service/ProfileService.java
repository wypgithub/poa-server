package com.poa.server.service;

import com.poa.server.entity.PoaEstateTrustee;
import com.poa.server.entity.PoaProfile;
import com.poa.server.repository.EstateTrusteeRepository;
import com.poa.server.repository.PageQueryDao;
import com.poa.server.repository.ProfileRepository;
import com.poa.server.util.Constants;
import com.poa.server.util.ResponseMsg;
import com.poa.server.vo.SearchParamVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceContext;


/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Service
public class ProfileService {
    @Autowired
    private PageQueryDao pageQueryDao;

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

        StringBuffer querySql = new StringBuffer();
        querySql.append(" select p.first_name,p.middle_name,p.last_name,count(d.id) as files");
        querySql.append(" from poa_document d,poa_profile p where d.profile_id=p.id");


        if(StringUtils.isNotBlank(paramVO.getFirstName())){
            querySql.append(String.format(" and p.first_name = '%s'", paramVO.getFirstName()));
        }
        if(StringUtils.isNotBlank(paramVO.getMiddleName())){
            querySql.append(String.format(" and p.middle_name = '%s'", paramVO.getMiddleName()));
        }
        if(StringUtils.isNotBlank(paramVO.getLastName())){
            querySql.append(String.format(" and p.last_name = '%s'", paramVO.getLastName()));
        }

        if(StringUtils.isNotBlank(paramVO.getDocumentType())){
            querySql.append(String.format(" and d.type = '%s'", paramVO.getDocumentType()));
        }

        querySql.append(" group by p.first_name,p.middle_name,p.last_name");


        return ResponseMsg.ok(pageQueryDao.findAll(querySql.toString(), paramVO.getPageNum(), paramVO.getPageSize()));
    }


}
