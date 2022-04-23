package com.poa.server.vo;

import lombok.Data;


@Data
public class SearchParamVO {
    private Integer pageNum;
    private Integer pageSize;

    private String firstName;
    private String middleName;
    private String lastName;
    private String documentType;
    private String documentStatus;
    private String province;
    private String virtuallyExecuted;
    private String startDate;
    private String endDate;
    private String showDecease;
    private String showClosed;


    private String letter ;
    private Boolean showAll;


}
