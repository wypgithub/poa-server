package com.poa.server.service;

import com.poa.server.entity.PoaRegistry;
import com.poa.server.repository.RegistryRepository;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class RegistryService {

    @Autowired
    private RegistryRepository registryRepository;

    public ResponseMsg saveRegistry(PoaRegistry registry) {
        registry.setCreatedTime(new Date());
        registry.setCreateBy(UserUtil.getUserId());
        registryRepository.save(registry);
        return ResponseMsg.ok();
    }


    public ResponseMsg listDocument(String email, String type) {

        return ResponseMsg.ok(registryRepository.findByTypeAndName(email, type));
    }



}
