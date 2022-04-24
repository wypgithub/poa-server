package com.poa.server.controller;


import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.entity.PoaProfile;
import com.poa.server.service.VaultService;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.RoleType;
import com.poa.server.vo.SearchParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/vault")
public class VaultController {

    @Autowired
    private VaultService vaultService;

    @PostMapping("/list")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg findList(@RequestBody SearchParamVO paramVO) {

        return vaultService.listAll(paramVO);
    }

    @PostMapping("/profile")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg save(@RequestBody PoaProfile profile) {

        return vaultService.save(profile);
    }

    @GetMapping("/openFiles")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg openFiles(@RequestParam String profileId, @RequestParam String documentIds) {

        return vaultService.openFiles(profileId, documentIds);
    }

    @GetMapping("/profile/{id}")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg findById(@PathVariable String id) {

        return vaultService.findById(id);
    }





}
