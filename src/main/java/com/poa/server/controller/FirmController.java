package com.poa.server.controller;


import cn.hutool.json.JSONObject;
import com.poa.server.annotation.AccessAuthorize;
import com.poa.server.entity.Firm;
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

    public FirmController(FirmService firmService) {
        this.firmService = firmService;
    }

    @PostMapping
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg save(@RequestBody Firm firm) {
        return firmService.save(firm);
    }

    @GetMapping("{id}")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg findById(@PathVariable String id) throws IOException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ilg1ZVhrNHh5b2pORnVtMWtsMll0djhkbE5QNC1jNTdkTzZRR1RWQndhTmsifQ.eyJpc3MiOiJodHRwczovL2N1cmFkZXZ0ZXN0LmIyY2xvZ2luLmNvbS9kYTZkYWE0Ny04NzAwLTQ5MDgtYTVhMC05OTBiY2NiNjQ5NTMvdjIuMC8iLCJleHAiOjE2NTAyNDczMDcsIm5iZiI6MTY1MDI0NjEwNywiYXVkIjoiOTY2MGFlODEtMTM5Ni00MjBhLWJmYzQtZTYzZDhhYjZlM2U3Iiwib2lkIjoiNzk3ZWJlZWQtODU3NC00NDRjLWJlNzMtZmEwNjhlYTY3ZGEzIiwic3ViIjoiNzk3ZWJlZWQtODU3NC00NDRjLWJlNzMtZmEwNjhlYTY3ZGEzIiwiZ2l2ZW5fbmFtZSI6IjQiLCJmYW1pbHlfbmFtZSI6IjIiLCJuYW1lIjoiNCAyIiwiZW1haWxzIjpbIjEzMTgwMDAzOTBAcXEuY29tIl0sInRmcCI6IkIyQ18xX1NpZ251cF9TaWduaW4iLCJub25jZSI6Ijc3MWFlMzY3LWUyOTEtNDc4Ni04NjFlLTY2OTRlMGYwNzVmYSIsInNjcCI6InUucmVhZCIsImF6cCI6Ijk2NjBhZTgxLTEzOTYtNDIwYS1iZmM0LWU2M2Q4YWI2ZTNlNyIsInZlciI6IjEuMCIsImlhdCI6MTY1MDI0NjEwN30.m08iCx6qZ10cx21tAqjee40RwOJ5BVt5Nm3JrI7VQq95ay4OVuHGfAATMJx_i-Q63kUn9n4SwWgOO1Rl8RoNvQkTLgyncbs9r2Zr13hZNFgnwHbrSd8RFccFqoV488otkf9gTvpug-fsGwsR6n4qHGEn4l-39I8HYBUw8d4IgZKcZpq62PsEQGtP4uvM8eEoJtesOGsI7UHUQSQvezOl01F8lHsV6WA4rnzsY2Jsvu14_d0P1EvTJ1i8wEmNpKDfaMknYdnZvtKpFwbrj5dRTBhB5uDvIAIpsCh3aNCSTC5xPOd9QVaSgeGURsNI7HndL8spXwS_nz9TrdneVJF1sw";

        System.out.println(azureAPI.usersMe(token));


        return firmService.findById(id);
    }

    @GetMapping("list")
    @AccessAuthorize(RoleType.SYSADMIN)
    public ResponseMsg findList() {
        return ResponseMsg.ok();
    }






}
