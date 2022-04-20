package com.poa.server.service.impl;

import com.poa.server.entity.PoaDocument;
import com.poa.server.repository.DocumentRepository;
import com.poa.server.service.DocumentService;
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
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;


    @Override
    public ResponseMsg save(PoaDocument document) {
        return ResponseMsg.ok(documentRepository.save(document));
    }

    @Override
    public ResponseMsg findById(String id) {
        return ResponseMsg.ok(documentRepository.findById(id));
    }

    @Override
    public ResponseMsg delete(String id) {
        documentRepository.deleteById(id);

        return ResponseMsg.ok();
    }

    @Override
    public ResponseMsg listAll(Integer pageNum, Integer pageSize) {
        return ResponseMsg.ok(documentRepository.findAll(PageRequest.of(pageNum, pageSize)));
    }
}
