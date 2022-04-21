package com.poa.server.controller;

import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.entity.PoaRegistry;
import com.poa.server.repository.RegistryRepository;
import com.poa.server.service.RegistryService;
import com.poa.server.util.ResponseMsg;
import com.poa.server.util.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/registry")
public class RegistryController {

    @Autowired
    private RegistryService registryService;

    @PostMapping()
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg listDocument(@RequestBody PoaRegistry registry) {

        return registryService.saveRegistry(registry);
    }

    @GetMapping("/{type}/{name}")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg listRegistry(@PathVariable String fullName, @PathVariable String name) {

        return registryService.listDocument(fullName, name);
    }


}
