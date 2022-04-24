package com.poa.server.service;

import cn.hutool.core.collection.CollUtil;
import com.poa.server.entity.PoaEstateTrustee;
import com.poa.server.entity.PoaProfile;
import com.poa.server.repository.DocumentRepository;
import com.poa.server.repository.EstateTrusteeRepository;
import com.poa.server.repository.SqlQueryDao;
import com.poa.server.repository.ProfileRepository;
import com.poa.server.util.Constants;
import com.poa.server.util.ResponseMsg;
import com.poa.server.vo.SearchParamVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Service
public class VaultService {
    @Autowired
    private SqlQueryDao sqlQueryDao;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private EstateTrusteeRepository estateTrusteeRepository;


    public ResponseMsg save(PoaProfile profile) {
        if (StringUtils.isBlank(profile.getId())){
            profile.setStatus(Constants.ProfileStatus.Open);
            if (!profileRepository.findByEmailAndStatus(profile.getEmail(), profile.getStatus()).isEmpty()){
                return ResponseMsg.error("Exist Profile associated with this email");
            }
        }else {
            if (!profileRepository.findByEmailAndStatusAndIdNot(profile.getEmail(), profile.getStatus(), profile.getId()).isEmpty()){
                return ResponseMsg.error("Exist Profile associated with this email");
            }
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

    private String getCondtionSql(SearchParamVO paramVO){
        StringBuffer querySql = new StringBuffer();
        if(StringUtils.isNotBlank(paramVO.getLetter())){
            querySql.append(String.format(" and left(p.last_name,1) = '%s'", paramVO.getLetter()));
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

        return querySql.toString();
    }

    public ResponseMsg listAll(SearchParamVO paramVO) {
        StringBuffer querySql = new StringBuffer();
        querySql.append(" select d.profile_id,p.first_name,p.middle_name,p.last_name,count(d.id) as files");
        querySql.append(" from poa_document d,poa_profile p where d.profile_id=p.id");
        querySql.append(getCondtionSql(paramVO));

        querySql.append(" group by p.first_name,p.middle_name,p.last_name");
        Page<Map<String, Object>> result = sqlQueryDao.findAll(querySql.toString(), paramVO.getPageNum(), paramVO.getPageSize());


        List<Map<String, Object>> list = result.getContent();
        for (Map<String, Object> map : list){
            StringBuffer querySql2 = new StringBuffer();
            querySql2.append(String.format(" select d.id from poa_document d,poa_profile p where d.profile_id='%s'", map.get("profile_id")));
            querySql2.append(getCondtionSql(paramVO));
            List<Map<String, Object>> resultList = sqlQueryDao.findList(querySql.toString());


            CollUtil.f

        }





        return ResponseMsg.ok(result);
    }

    public ResponseMsg openFiles(String profileId, String documentIds) {
        Map<String, Object> result = new HashMap<>();

        StringBuffer querySql = new StringBuffer("select p.type,p.status,p.update_time,");
        querySql.append(" (select count(id) from poa_registry where document_id = p.id) as registry,");
        querySql.append(String.format("STUFF((select ','+id from poa_file where type='%s' and ref_id = p.id for xml path('')), 1, 1, '') as direction,",
                Constants.FileType.Direction));
        querySql.append(String.format("STUFF((select ','+id from poa_file where type='%s' and ref_id = p.id for xml path('')), 1, 1, '') as aoe",
                Constants.FileType.AOE));
        querySql.append(String.format(" from poa_document p where id in('%s')", documentIds.replaceAll(",", "','")));

        result.put("documents", sqlQueryDao.findList(querySql.toString()));
        result.put("profile", profileRepository.findById(profileId));

        return ResponseMsg.ok(result);
    }


}
