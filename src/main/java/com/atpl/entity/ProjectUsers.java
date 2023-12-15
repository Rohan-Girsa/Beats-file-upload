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
@Table(name = "project_users")
public class ProjectUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="project_id")
    private Integer projectId;
    @Column(name="company_id")
    private Integer companyId;
    @Column(name="user_id")
    private Integer userId;
    private Byte istype;
    @Column(name="default_email")
    private Byte defaultEmail;
    @Column(name="dt_visited")
    private Date dtVisited;
    @Column(name="role_id")
    private Integer roleId;
}
