package org.ptit.okrs.core_authentication.repository;

import org.ptit.okrs.core_authentication.entity.AuthAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthAccountRepository extends JpaRepository<AuthAccount, String> {
  Optional<AuthAccount> findFirstByUserId(String userId);

  boolean existsByUserId(String userId);
  boolean existsByUsername(String username);
}
