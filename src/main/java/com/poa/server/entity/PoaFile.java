package com.poa.server.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description
 * @Author WYP
 * @Date 2022/4/19
 */
@Data
@Entity
@Table
public class PoaFile {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    /** Constants.FileType **/
    private String type;

    private String refId;
    private String name;
    private String path;



}
