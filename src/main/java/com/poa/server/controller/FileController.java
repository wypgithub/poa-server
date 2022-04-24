package com.poa.server.controller;

import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.exception.PoaException;
import com.poa.server.service.FileService;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg uploadFile(@RequestParam("file") MultipartFile file,
                                  @RequestParam String type, @RequestParam String refId){

        return fileService.uploadFile(file, type, refId);
    }

    @GetMapping("download/{fileId}")
    @AccessAuthorize(RoleType.ALL)
    public void downloadFile(@PathVariable String fileId, HttpServletRequest request, HttpServletResponse response) {
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
            if(file != null){
                file.delete();
            }
        }
    }


}
