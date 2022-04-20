package com.poa.server.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.poa.server.entity.PoaDocument;
import com.poa.server.entity.PoaFile;
import com.poa.server.exception.PoaException;
import com.poa.server.repository.DocumentRepository;
import com.poa.server.repository.FileRepository;
import com.poa.server.util.Constants;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.UserUtil;
import com.poa.server.vo.PoaDocumentVO;
import com.poa.server.vo.PoaFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

        String userId = UserUtil.getUserId();
        poaFile.setCreateBy(userId);
        poaFile.setUpdateBy(userId);

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

}