package com.poa.server.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

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

    private String createBy;
    private String updateBy;
    private Date createdTime;
    private Date updateTime;
}