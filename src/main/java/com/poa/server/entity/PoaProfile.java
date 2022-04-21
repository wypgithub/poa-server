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

    private String firstName;
    private String middleName;
    private String lastName;
    /** Also Known As **/
    @Column(name = "first_name_2")
    private String firstName2;
    @Column(name = "middle_name_2")
    private String middleName2;
    @Column(name = "lastName_2")
    private String lastName2;
    private Date dateOfBirth;
    private String occupation;
    private String email;
    private String phoneNumber;
    @Column(name = "address_1")
    private String address1;
    @Column(name = "address_2")
    private String address2;
    private String city;
    private String province;
    private String postalCode;

    private String createBy;
    private String updateBy;
    private Date createdTime;
    private Date updateTime;
}
