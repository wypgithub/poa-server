package com.poa.server.service;

import com.poa.server.entity.PoaFirm;
import com.poa.server.util.ResponseMsg;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/15
 */
public interface FirmService {
    ResponseMsg save(PoaFirm firm);

    ResponseMsg findById(String id);

    ResponseMsg delete(String id);

    ResponseMsg listAll();



}
