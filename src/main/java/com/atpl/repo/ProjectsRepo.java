package com.atpl.repo;

import com.atpl.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProjectsRepo extends JpaRepository<Projects,Integer> {
    int countByName(String name);
    int countById(int id);
    Projects findTopByName(String name);

    Projects findTopById(int projectId);
    @Query(value = "select MD5(UUID_SHORT()) as uniqueId",nativeQuery = true)
    String genCompanyUniqId();
}
