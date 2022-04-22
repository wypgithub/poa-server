package com.poa.server.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/19
 */
@Data
@Entity
@Table
public class PoaEstateTrustee {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String profileId;

    private String fullName;
    private String email;
    private String phoneNumber;

    @CreatedBy
    private String createBy;
    @CreatedDate
    private Date createdTime;
    @LastModifiedBy
    private String updateBy;
    @LastModifiedDate
    private Date updateTime;
}
