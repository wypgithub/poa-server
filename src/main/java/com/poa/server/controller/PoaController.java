package com.poa.server.controller;

import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.entity.PoaDocument;
import com.poa.server.entity.PoaRegistry;
import com.poa.server.exception.PoaException;
import com.poa.server.service.FileService;
import com.poa.server.service.PoaService;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.RoleType;
import com.poa.server.vo.PoaDocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/saveDocument")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg saveDocument(@RequestBody List<PoaDocumentVO> documents) {
        return poaService.saveDocument(documents);
    }

    @GetMapping("/{profileId}/document")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg listDocument(@PathVariable String profileId) {

        return poaService.listDocument(profileId);
    }





}
