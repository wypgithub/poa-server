package com.poa.server.service;

import com.poa.server.entity.PoaProfile;
import com.poa.server.repository.ProfileRepository;
import com.poa.server.util.Constants;
import com.poa.server.util.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;


    public ResponseMsg save(PoaProfile profile) {
        profile.setDate(new Date());
        profile.setStatus(Constants.ProfileStatus.Open);
        return ResponseMsg.ok(profileRepository.save(profile));
    }

    public ResponseMsg findById(String id) {
        return ResponseMsg.ok(profileRepository.findById(id));
    }

    public ResponseMsg delete(String id) {
        profileRepository.deleteById(id);
        return ResponseMsg.ok();
    }

    public ResponseMsg listAll(Integer pageNum, Integer pageSize) {
        return ResponseMsg.ok(profileRepository.findAll(PageRequest.of(pageNum, pageSize)));
    }
}
