package com.crashcourse.SpringBootTraining.AccountData.repository;

import com.crashcourse.SpringBootTraining.AccountData.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByEmail(String email);
}
