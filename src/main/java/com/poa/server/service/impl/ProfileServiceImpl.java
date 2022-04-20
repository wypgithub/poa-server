package com.poa.server.service.impl;

import com.poa.server.entity.PoaProfile;
import com.poa.server.repository.ProfileRepository;
import com.poa.server.service.ProfileService;
import com.poa.server.util.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;


    @Override
    public ResponseMsg save(PoaProfile profile) {
        return ResponseMsg.ok(profileRepository.save(profile));
    }

    @Override
    public ResponseMsg findById(String id) {
        return ResponseMsg.ok(profileRepository.findById(id));
    }

    @Override
    public ResponseMsg delete(String id) {
        profileRepository.deleteById(id);
        return ResponseMsg.ok();
    }

    @Override
    public ResponseMsg listAll(Integer pageNum, Integer pageSize) {
        return ResponseMsg.ok(profileRepository.findAll(PageRequest.of(pageNum, pageSize)));
    }
}
