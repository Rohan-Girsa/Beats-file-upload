package com.atpl.repo;

import com.atpl.entity.ProjectUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProjectUsersRepo extends JpaRepository<ProjectUsers,Integer> {
    ProjectUsers findTopByProjectIdAndUserId(int projectId, int userId);
}
