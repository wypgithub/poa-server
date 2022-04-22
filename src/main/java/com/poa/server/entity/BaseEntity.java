package com.poa.server.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@MappedSuperclass
public class BaseEntity {
    @CreatedBy
    private String createBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @LastModifiedBy
    private String updateBy;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}
