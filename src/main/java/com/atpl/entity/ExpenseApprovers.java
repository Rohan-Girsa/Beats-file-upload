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
@Table(name = "expense_approvers")
public class ExpenseApprovers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="company_id")
    private Integer companyId;
    @Column(name="project_id")
    private Integer projectId;
    @Column(name="user_id")
    private Integer userId;
    @Column(name="user_type")
    private Integer userType;
    @Column(name="user_hierarchy")
    private Integer userHierarchy;
    @Column(name="is_active")
    private Integer isActive;
    @Column(name="is_deleted")
    private Integer isDeleted;
    private Date created;
    private Date modified;
}
