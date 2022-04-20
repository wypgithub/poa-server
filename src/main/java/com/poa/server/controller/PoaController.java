package com.poa.server.controller;

import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.entity.PoaDocument;
import com.poa.server.exception.PoaException;
import com.poa.server.service.FileService;
import com.poa.server.service.PoaService;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.RoleType;
import com.poa.server.vo.PoaDocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Slf4j
@RestController
@RequestMapping("api/poa")
public class PoaController {

    @Autowired
    private PoaService poaService;
    @Autowired
    private FileService fileService;

    @PostMapping("/upload/{fileType}")
    @AccessAuthorize(RoleType.ALL)
    public ResponseMsg uploadFile(@PathVariable String fileType, @RequestParam("file") MultipartFile file) throws IOException {



        return poaService.uploadFile(fileType, file);
    }

    @GetMapping("download/{fileId}")
    @AccessAuthorize(RoleType.ALL)
    public ResponseEntity<?> downloadFile(@PathVariable String fileId,
                                          HttpServletRequest request, HttpServletResponse response) {
        File file = null;
        FileInputStream fis = null;
        try {
            file = fileService.download(fileId);

            //set response
            response.setCharacterEncoding(request.getCharacterEncoding());
            response.setContentType("application/octet-stream");

            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();

        } catch (Exception e) {
            log.error("file download error! redownload");
            throw new PoaException("File download failed, please try again later!");
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            file.delete();
        }

        return null;
    }


    @PostMapping("/saveDocument")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg saveDocument(@RequestBody List<PoaDocumentVO> documents) {
        return poaService.saveDocument(documents);
    }




}
