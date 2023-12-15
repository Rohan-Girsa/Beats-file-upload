package com.atpl.repo;

import com.atpl.entity.ExpenseApprovers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ExpenseApproversRepo extends JpaRepository<ExpenseApprovers,Integer> {
    ExpenseApprovers findTopByProjectIdAndUserHierarchyAndIsDeleted(int projectId, int userHierarchy, int idDeleted);
    ExpenseApprovers findTopByProjectIdAndUserId(int projectId,int userId);
}
