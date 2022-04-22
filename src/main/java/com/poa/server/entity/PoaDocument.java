package com.poa.server.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table
public class PoaDocument extends BaseEntity{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String profileId;
    private String type;
    private String name;
    private String status;

    /** Original、Copy of Original、Notarial Copy **/
    private String uploading;
    private String hardCopy;
    private String province;
    private String virtuallyExecuted;
    private String affidavit;
    private String declaration;
    private String flag;


    private Integer step;
    private String consentVault;






   /* private String original;
    private String signed;
    private String usedProvince;
    private String signedProvince;
    private Date signedDate;
    private String signedUsingVideo;
    private String referStatute;
*/


}
