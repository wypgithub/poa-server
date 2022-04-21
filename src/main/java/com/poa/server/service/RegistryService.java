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
        return ResponseMsg.ok(registryRepository.save(registry));
    }


    public ResponseMsg listDocument(String fullName, String name) {

        return ResponseMsg.ok(registryRepository.findByTypeAndName(fullName, name));
    }



}
