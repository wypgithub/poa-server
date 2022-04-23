package com.poa.server.service;

import com.poa.server.entity.PoaEstateTrustee;
import com.poa.server.entity.PoaProfile;
import com.poa.server.repository.DocumentRepository;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


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
    private DocumentRepository documentRepository;
    @Autowired
    private EstateTrusteeRepository estateTrusteeRepository;


    public ResponseMsg save(PoaProfile profile) {
        if (StringUtils.isBlank(profile.getId())){
            profile.setStatus(Constants.ProfileStatus.Open);
        }else {
            //update all documents for the grantor TODO
            if (Constants.ProfileStatus.Deceased.equals(profile.getStatus())){
                documentRepository.updateDeceasedStatus(profile.getId());
            }else if (Constants.ProfileStatus.Inactive.equals(profile.getStatus())){
                documentRepository.updateInactiveStatus(profile.getId());
            }
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
        StringBuffer querySql = new StringBuffer();
        querySql.append(" select p.first_name,p.middle_name,p.last_name,count(d.id) as files");
        querySql.append(" from poa_document d,poa_profile p where d.profile_id=p.id");

        if(StringUtils.isNotBlank(paramVO.getLetter())){
            querySql.append(String.format(" and left(p.last_name,1) = '%s'", paramVO.getLetter()));
        }else if(StringUtils.isNotBlank(paramVO.getLetter())){
            querySql.append(String.format(" and p.first_name = '%s'", paramVO.getFirstName()));
        }else{
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
            if(StringUtils.isNotBlank(paramVO.getDocumentStatus())){
                querySql.append(String.format(" and d.status = '%s'", paramVO.getDocumentStatus()));
            }
            if(StringUtils.isNotBlank(paramVO.getStartDate())){
                if(StringUtils.isNotBlank(paramVO.getDocumentStatus())){
                    querySql.append(String.format(" and d.update_time >= '%s'", paramVO.getStartDate()));
                }else {
                    querySql.append(String.format(" and d.created_time > '%s'", paramVO.getStartDate()));
                }
            }
            if(StringUtils.isNotBlank(paramVO.getEndDate())){
                querySql.append(String.format(" and d.update_time <= '%s'", paramVO.getEndDate  ()));
            }
        }

        if(StringUtils.isBlank(paramVO.getShowDecease())){
            querySql.append(String.format(" and p.status != 'Decease'"));
        }
        if(StringUtils.isBlank(paramVO.getShowClosed())){
            querySql.append(String.format(" and p.status != 'Inactive'"));
        }

        querySql.append(" group by p.first_name,p.middle_name,p.last_name");

        return ResponseMsg.ok(pageQueryDao.findAll(querySql.toString(), paramVO.getPageNum(), paramVO.getPageSize()));
    }

    public ResponseMsg openFiles(String profileId, String documentIds) {
        Map<String, Object> result = new HashMap<>();

        result.put("profile", profileRepository.findById(profileId));

        result.put("documents", documentRepository.findAllById(Arrays.asList(documentIds.split(","))););

        return ResponseMsg.ok(result);
    }


}
