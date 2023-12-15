package com.atpl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "projects")
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "uniq_id")
    private String uniqId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "company_id")
    private Integer companyId;
    private String name;
    @Column(name = "short_name")
    private String shortName;
    private String description;
    @Column(name = "workflow_id")
    private Integer workflowId;
    @Column(name = "project_type")
    private Byte projectType;
    @Column(name = "default_assign")
    private Integer defaultAssign;
    private Byte isactive;
    private Integer manager;
    @Column(name = "dt_created")
    private Date dtCreated;
    private Integer initiator;
}
