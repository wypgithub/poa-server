package com.poa.server.service;

import com.poa.server.entity.PoaDocument;
import com.poa.server.entity.PoaRegistry;
import com.poa.server.repository.DocumentRepository;
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
    @Autowired
    private DocumentRepository documentRepository;


    public ResponseMsg saveRegistry(PoaRegistry registry) {
        registryRepository.save(registry);

        PoaDocument document = documentRepository.findById(registry.getDocumentId()).get();
        documentRepository.save(document);

        return ResponseMsg.ok();
    }


    public ResponseMsg listDocument(String email, String type) {

        return ResponseMsg.ok(registryRepository.findByTypeAndName(email, type));
    }



}
