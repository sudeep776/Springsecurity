package com.spring_security.security.Repository;

import com.spring_security.security.Models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserAccount,Integer> {
    List<UserAccount> findByEmail(String email);
}
