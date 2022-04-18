package com.poa.server.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoBO {

    private String lastName;

    private String firstName;

//    private String sex;
//
//    private Date dateOfBirth;

    private String email;

    private String countryCode;

    private String phoneNumber;

    private String address1;

    private String address2;

    private String city;

    private String provinceOrTerritory;

    private String postalCode;

    private String faxNumber;

    private String role;

    private String firmId;

    private String id;

    private String preparedType;

    private String state;

    private Boolean isSDM;

    private String arragement;

    private String sdmRoleName;

}
