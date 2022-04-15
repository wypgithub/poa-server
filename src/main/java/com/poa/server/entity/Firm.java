package com.poa.server.entity;


import lombok.Data;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "firm")
public class Firm {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String name;

    private String certifiedRegion;

    private String lawFirmRegistrationNumber;

    private Integer numberOfAccounts;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    private String city;

    private String postalCode;

    private String province;

    private Date createdTime;

    private Date updatedTime;



}
