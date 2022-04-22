package com.poa.server.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @description Profile
 * @Author WYP
 * @Date 2022/4/19
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PoaProfile extends BaseEntity{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String fileNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fileOpenDate;
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


    /** Open、Deceased、Incapable、Inactive **/
    private String status;
    private String comments;
    private Date deathDate;

    @Transient
    List<PoaEstateTrustee> estateTrustee;


}
