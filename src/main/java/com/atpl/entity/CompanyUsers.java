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
@Table(name = "company_users")
public class CompanyUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "company_id")
    private Integer companyId;
    @Column(name = "company_uniq_id")
    private String companyUniqueId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_type")
    private Integer userType;
    @Column(name = "is_active")
    private Integer isActive;
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "act_date")
    private Date actDate;
    @Column(name = "created")
    private Date created;
    @Column(name = "modified")
    private Date modified;
}
