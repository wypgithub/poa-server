package com.poa.server.controller;


import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.entity.PoaFirm;
import com.poa.server.service.FirmService;
import com.poa.server.util.AzureAPI;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/firm")
public class FirmController {

    @Autowired
    private FirmService firmService;
    @Autowired
    private AzureAPI azureAPI;


    @PostMapping
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg save(@RequestBody PoaFirm firm) {
        return firmService.save(firm);
    }

    @GetMapping("{id}")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg findById(@PathVariable String id) throws IOException {

        return firmService.findById(id);
    }

    @GetMapping("list")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg findList() {
        return ResponseMsg.ok();
    }






}
