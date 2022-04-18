package com.poa.server.controller;


import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.entity.Firm;
import com.poa.server.service.FirmService;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/firm")
public class FirmController {

    @Autowired
    private FirmService firmService;

    @PostMapping
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg save(@RequestBody Firm firm) {
        return firmService.save(firm);
    }

    @GetMapping("{id}")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg findById(@PathVariable String id) {
        return firmService.findById(id);
    }

    @GetMapping("list")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg findList() {
        return firmService.listAll();
    }

    @GetMapping("delete/{id}")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg delete(@PathVariable String id) {
        return firmService.delete(id);
    }




}
