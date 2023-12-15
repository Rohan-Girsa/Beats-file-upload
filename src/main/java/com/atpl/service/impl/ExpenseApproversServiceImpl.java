package com.atpl.service.impl;

import com.atpl.entity.ExpenseApprovers;
import com.atpl.repo.ExpenseApproversRepo;
import com.atpl.service.ExpenseApproversService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ExpenseApproversServiceImpl implements ExpenseApproversService {
    @Value("${CompanyId}")
    private Integer companyId;
    @Autowired
    private ExpenseApproversRepo expenseApproversRepo;
    @Override
    public void saveExpenseApprovers(int projectId, int userId, int userHierarchy) {
        log.info("<--- Creating Expense Approvers Hierarchy --->");
        try{
            ExpenseApprovers e = expenseApproversRepo.findTopByProjectIdAndUserId(projectId,userId);
            if(e == null){
                log.info("Adding " + userId + " into expense approvers with project ID " + projectId
                        + " with hierarchy " + userHierarchy);
                ExpenseApprovers approver = createExpenseApprover(projectId, userId, userHierarchy);
                expenseApproversRepo.save(approver);
            }else{
                log.info(userId + " already present in expense approvers for project " + projectId);
            }
        }catch (Exception e){
            log.info("Expense approvers Exception "+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateExpenseApprovers(int projectId, int userId, int userHierarchy) {
        log.info("<--- Updating Expense Approvers Hierarchy --->");
        try{
            ExpenseApprovers e = expenseApproversRepo.findTopByProjectIdAndUserHierarchyAndIsDeleted(projectId,userHierarchy,0);
            if(e != null){
                if(userId != e.getUserId()){
                    ExpenseApprovers ea = expenseApproversRepo.findTopByProjectIdAndUserId(projectId,userId);
                    if(ea != null){
                        e.setUserHierarchy(null);
                        e.setIsDeleted(1);
                        e.setModified(new Date());
                        log.info("Deactivating approver "+e.getUserId()+" at level "+userHierarchy+ " for project ID "+projectId);
                        expenseApproversRepo.save(e);
                        ea.setUserHierarchy(userHierarchy);
                        ea.setModified(new Date());
                        ea.setIsDeleted(0);
                        log.info("Activating approver "+e.getUserId()+" at level "+userHierarchy+ " for project ID "+projectId);
                        expenseApproversRepo.save(ea);
                    }else{
                        e.setUserHierarchy(null);
                        e.setIsDeleted(1);
                        e.setModified(new Date());
                        log.info("Deactivating approver "+e.getUserId()+" at level "+userHierarchy+ " for project ID "+projectId);
                        expenseApproversRepo.save(e);
                        ExpenseApprovers approvers = createExpenseApprover(projectId,userId,userHierarchy);
                        expenseApproversRepo.save(approvers);
                        log.info("Adding approver "+userId+" at level "+userHierarchy+ " for project ID "+projectId);
                    }
                }else{
                    log.info("No Change in approver at level "+userHierarchy+ " for project ID "+projectId);
                }
            }else{
                ExpenseApprovers ea = createExpenseApprover(projectId, userId, userHierarchy);
                log.info("Adding approver "+ea.getUserId()+" at level "+userHierarchy+ " for project ID "+projectId);
                expenseApproversRepo.save(ea);
            }
        }catch (Exception e){
            log.info("Expense approvers Exception "+e.getMessage());
            e.printStackTrace();
        }
    }

    private ExpenseApprovers createExpenseApprover(int projectId, int userId, int hierarchy) {
        ExpenseApprovers approver = new ExpenseApprovers();
        approver.setCompanyId(companyId);
        approver.setProjectId(projectId);
        approver.setUserId(userId);
        approver.setUserHierarchy(hierarchy);
        approver.setUserType(1);
        approver.setIsActive(1);
        approver.setIsDeleted(0);
        approver.setCreated(new Date());
        approver.setModified(new Date());
        return approver;
    }
}
