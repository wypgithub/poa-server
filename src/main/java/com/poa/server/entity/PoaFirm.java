package com.poa.server.entity;


import lombok.Data;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table
public class PoaFirm {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String name;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    private String city;

    private String postalCode;

    private String province;

    private String adminLastName;

    private String adminMiddleName;

    private String adminFirstName;

    private String adminEmail;


    private Date createdTime;

    private Date updatedTime;



}
