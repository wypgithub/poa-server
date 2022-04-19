package com.poa.server.service.impl;

import com.poa.server.entity.Firm;
import com.poa.server.repository.FirmRepository;
import com.poa.server.service.FirmService;
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
public class FirmServiceImpl implements FirmService {

    @Autowired
    private FirmRepository firmRepository;


    /**
     * save firm info
     *
     * @param firm firm info
     * @return saved firm
     */
    @Override
    public ResponseMsg save(Firm firm) {
        Date date = new Date();
        if (StringUtils.isBlank(firm.getId())) {
            firm.setCreatedTime(date);
        }
        firm.setUpdatedTime(date);

        firmRepository.save(firm);

        return ResponseMsg.ok();
    }

    @Override
    public ResponseMsg findById(String id) {

        return ResponseMsg.ok(firmRepository.findById(id));
    }

    @Override
    public ResponseMsg delete(String id) {
        firmRepository.deleteById(id);

        return ResponseMsg.ok();
    }

    @Override
    public ResponseMsg listAll() {

        return ResponseMsg.ok(firmRepository.findAll());
    }


}
