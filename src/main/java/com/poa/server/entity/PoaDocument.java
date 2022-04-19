package com.poa.server.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table
public class PoaDocument {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String profileId;
    private String type;
    private String name;
    private String status;

    /** Original、Copy of Original、Notarial Copy **/
    private String fileType;
    private String hardCopy;
    private String executedProvince;
    private String executed;
    private String flag;


    private Integer currentStep;


   /* private String original;
    private String signed;
    private String usedProvince;
    private String signedProvince;
    private Date signedDate;
    private String signedUsingVideo;
    private String referStatute;
*/

    private String createBy;
    private String updateBy;
    private Date createdTime;
    private Date updateTime;

}
