package com.poa.server.controller;


import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.entity.PoaProfile;
import com.poa.server.service.ProfileService;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.RoleType;
import com.poa.server.vo.SearchParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/vault")
public class VaultController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/profile")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg save(@RequestBody PoaProfile profile) {
        return profileService.save(profile);
    }


    @GetMapping("/profile/{id}")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg findById(@PathVariable String id) {

        return profileService.findById(id);
    }

    @PostMapping("/profile/list")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg findList(@RequestBody SearchParamVO paramVO) {
        return profileService.listAll(paramVO);
    }


    @GetMapping("/openFiles")
    @AccessAuthorize(RoleType.LAWYER)
    public ResponseMsg openFiles(@RequestParam String profileId, @RequestParam String documentIds) {

        return profileService.openFiles(profileId, documentIds);
    }




}
