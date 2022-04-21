package com.poa.server.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Data
public class PoaFileVO implements Serializable {
    private String fileId;
    private String fileName;

    public PoaFileVO() {
    }

    public PoaFileVO(String fileId, String fileName) {
        this.fileId = fileId;
        this.fileName = fileName;
    }
}
