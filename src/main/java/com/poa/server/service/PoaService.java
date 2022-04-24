package com.poa.server.service;

import cn.hutool.core.bean.BeanUtil;
import com.poa.server.entity.PoaDocument;
import com.poa.server.entity.PoaFile;
import com.poa.server.entity.PoaPermission;
import com.poa.server.entity.PoaShared;
import com.poa.server.repository.DocumentRepository;
import com.poa.server.repository.FileRepository;
import com.poa.server.repository.PermissionRepository;
import com.poa.server.repository.SharedRepository;
import com.poa.server.util.Constants;
import com.poa.server.util.ResponseMsg;
import com.poa.server.vo.PoaDocumentVO;
import com.poa.server.vo.PoaFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Service
public class PoaService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private SharedRepository sharedRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private PermissionRepository permissionRepository;





    @Transactional(rollbackFor = Exception.class)
    public ResponseMsg saveDocument(List<PoaDocumentVO> documents) {
        if (documents == null || documents.isEmpty()){
            ResponseMsg.msg("param is empty!");
        }

        for(PoaDocumentVO doc : documents){
            PoaDocument document = new PoaDocument();
            BeanUtil.copyProperties(doc, document);
            BeanUtil.copyProperties(doc.getForm(), document);
            document = documentRepository.save(document);

            for (PoaFileVO fileVO : doc.getFiles()){
                Optional<PoaFile> fileOptional = fileRepository.findById(fileVO.getFileId());
                if (!fileOptional.isPresent()){
                    continue;
                }
                PoaFile poaFile = fileOptional.get();
                poaFile.setRefId(document.getId());
                fileRepository.save(poaFile);
            }
        }
        return ResponseMsg.ok();
    }

    public ResponseMsg listDocument(String profileId) {
        List<PoaDocument> docList = documentRepository.findByProfileId(profileId);
        if (docList.isEmpty()){
            return ResponseMsg.error("no data");
        }

        List<PoaDocumentVO> documents = new ArrayList<>();
        for (PoaDocument doc: docList){
            PoaDocumentVO docVO = new PoaDocumentVO();
            BeanUtil.copyProperties(doc, docVO);

            List<PoaFile> fileList = fileRepository.findByRefId(doc.getId());
            if (!fileList.isEmpty()){
                List<PoaFileVO> files = new ArrayList<>();
                for (PoaFile file: fileList){
                    files.add(new PoaFileVO(file.getId(), file.getName()));
                }
                docVO.setFiles(files);
            }
            documents.add(docVO);
        }

        return ResponseMsg.ok(documents);
    }



    public ResponseMsg directionRelease(PoaPermission Permission) {
        Permission.setType(Constants.PermissionType.Registry);
        permissionRepository.save(Permission);


        return ResponseMsg.ok();
    }

    public ResponseMsg shared(PoaShared shared) {
        if (!sharedRepository.findByEmailAndDocumentId(shared.getEmail(), shared.getDocumentId()).isEmpty()){
            return ResponseMsg.error("Duplicated, Please change the email");
        }

        sharedRepository.save(shared);

        return ResponseMsg.ok();
    }





}
