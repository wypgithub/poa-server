package com.poa.server.service;

import com.poa.server.entity.Firm;
import com.poa.server.util.ResponseMsg;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/15
 */
public interface FirmService {
    ResponseMsg save(Firm firm);

    ResponseMsg findById(String id);



}
