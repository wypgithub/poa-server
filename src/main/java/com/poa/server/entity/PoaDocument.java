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

    private String grantorId;






    private String createBy;
    private String updateBy;
    private Date createdTime;
    private Date updateTime;








    /**
     * poa name
     */
    private String name;

    /**
     * POA TYPE:
     *      compose
     *      upload
     */
    private String type;

    /**
     * only type='upload':
     *      Original Document
     *      Certified/Notarized
     *      Photocopy
     */
    private String uploadPoaFileType;

    /**
     * grantor id
     */
    private String grantor;

    /**
     * prepared id
     */
    private String prepared;

    /**
     * POA TYPE:
     *      Personal Health
     *      Finance/Property
     */
    private String poaType;

    /**
     * only POA TYPE='Finance/Property'
     */
    private String poaParam;

    /**
     * poa create in region?
     */
    private String createdIn;

    /**
     * poa activated in region?
     */
    private String activatedIn;

    /**
     * activation start time
     */
    private Date timeFrom;

    /**
     * activation end time
     */
    private Date timeTo;

    /**
     * this field is file id
     * relation to affidavit for activation
     * can be none
     */
    private String affidavitForActivation;

    private String capacityAssessment;

    /**
     * email address
     */
//    private String capacityAssessmentEmail;

    /**
     * other contact information
     */
//    private String capacityAssessmentOtherInformation;

    /**
     * consent and direction
     * relation to consent and direction
     */
    private String consentAndDirection;

    private String sdmMethod;

    /**
     * poa file id
     */
    private String fileId;

    private String sendNotification;

    /**
     * poa status
     */
    private String poaStatus;

    private Integer currentStep;



    private String poaIdStr;

    private String preparedType;

    private String searchFlag;

    @Transient
    private String authority;

    @Transient
    private String grantorName;

    @Transient
    private String preparedBy;

    @Transient
    private String fileIdStr;

    @Transient
    private String createdTimeStr;



}
