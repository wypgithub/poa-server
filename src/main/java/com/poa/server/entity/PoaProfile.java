package com.poa.server.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @description Profile
 * @Author WYP
 * @Date 2022/4/19
 */
@Data
@Entity
@Table
public class PoaProfile {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String fileNumber;
    private Date fileOpenDate;
    /** Open、Deceased、Incapable、Inactive **/
    private String status;
    private Date date;
    private String comments;


    private String createBy;
    private String updateBy;
    private Date createdTime;
    private Date updateTime;
}
