package com.poa.server.controller;


import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.entity.PoaProfile;
import com.poa.server.service.ProfileService;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@RestController
@RequestMapping("api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg save(@RequestBody PoaProfile profile) {
        return profileService.save(profile);
    }

    @GetMapping("{id}")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg findById(@PathVariable String id) {

        return profileService.findById(id);
    }

    @GetMapping("list")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg findList(Integer pageNum, Integer pageSize) {
        return profileService.listAll(pageNum, pageSize);
    }







}
