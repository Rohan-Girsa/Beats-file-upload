package com.atpl.service;

public interface ExpenseApproversService {
    void saveExpenseApprovers(int projectId, int userId, int userHierarchy);
    void updateExpenseApprovers(int projectId, int userId, int userHierarchy);
}
