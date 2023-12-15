package com.atpl.repo;

import com.atpl.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<Users,Integer> {
    Users findTopByEmail(String email);

    @Query(nativeQuery = true, value = "select MD5(UUID_SHORT()) as uniq_id")
    String genMD5();
}
