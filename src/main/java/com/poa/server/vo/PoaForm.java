package com.poa.server.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/20
 */
@Data
public class PoaForm implements Serializable {
    private String uploading;
    private String hardCopy;
    private String province;
    private String virtuallyExecuted;
    private String affidavit;
    private String declaration;
    private String flag;

}
