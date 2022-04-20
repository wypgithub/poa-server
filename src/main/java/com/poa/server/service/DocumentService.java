package com.poa.server.service;

import com.poa.server.entity.PoaDocument;
import com.poa.server.util.ResponseMsg;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
public interface DocumentService {
    ResponseMsg save(PoaDocument firm);

    ResponseMsg findById(String id);

    ResponseMsg delete(String id);

    ResponseMsg listAll(Integer pageNum, Integer pageSize);

}
