package com.atpl.repo;

import com.atpl.entity.CompanyUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CompanyUsersRepo extends JpaRepository<CompanyUsers,Integer> {
    @Query(value = "Select MD5(UUID_SHORT()) as uniqId",nativeQuery = true)
    String genUniqId();
}
