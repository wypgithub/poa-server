package com.poa.server.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Data
public class PoaDocumentVO implements Serializable {
    private String profileId;
    private String id;
    private String type;
    private String name;
    private List<PoaFileVO> files;
    private PoaForm form;
}
