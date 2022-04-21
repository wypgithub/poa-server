package com.poa.server.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.poa.server.entity.PoaDocument;
import com.poa.server.entity.PoaFile;
import com.poa.server.entity.PoaPermission;
import com.poa.server.entity.PoaRegistry;
import com.poa.server.exception.PoaException;
import com.poa.server.repository.DocumentRepository;
import com.poa.server.repository.FileRepository;
import com.poa.server.repository.PermissionRepository;
import com.poa.server.repository.RegistryRepository;
import com.poa.server.util.Constants;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.UserUtil;
import com.poa.server.vo.PoaDocumentVO;
import com.poa.server.vo.PoaFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private FileService fileService;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public ResponseMsg uploadFile(String fileType, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseMsg.msg("file can not be null!");
        }

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        // generate temporary file
        final File temporaryFile = File.createTempFile(UUID.fastUUID().toString(), suffix);

        // MultipartFile to File
        file.transferTo(temporaryFile);

        fileService.uploadFileToStorage(temporaryFile);

        PoaFile poaFile = new PoaFile();
        poaFile.setType(fileType);
        poaFile.setName(fileName);
        poaFile.setAzureFileName(temporaryFile.getName());

        poaFile.setCreatedTime(new Date());
        poaFile.setCreateBy(UserUtil.getUserId());

        return ResponseMsg.ok(fileRepository.save(poaFile).getId());
    }


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



}
