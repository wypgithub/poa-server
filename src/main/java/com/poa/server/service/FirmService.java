package com.poa.server.service;

import com.poa.server.entity.PoaFirm;
import com.poa.server.repository.FirmRepository;
import com.poa.server.util.ResponseMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/15
 */
@Service
public class FirmService{

    @Autowired
    private FirmRepository firmRepository;


    /**
     * save firm info
     *
     * @param firm firm info
     * @return saved firm
     */
    public ResponseMsg save(PoaFirm firm) {
        Date date = new Date();
        if (StringUtils.isBlank(firm.getId())) {
            firm.setCreatedTime(date);
        }
        firm.setUpdatedTime(date);

        firmRepository.save(firm);

        return ResponseMsg.ok();
    }

    public ResponseMsg findById(String id) {

        return ResponseMsg.ok(firmRepository.findById(id));
    }

    public ResponseMsg delete(String id) {
        firmRepository.deleteById(id);

        return ResponseMsg.ok();
    }

    public ResponseMsg listAll() {

        return ResponseMsg.ok(firmRepository.findAll());
    }


}
