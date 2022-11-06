package org.ptit.okrs.core_authentication.repository;

import org.ptit.okrs.core_authentication.entity.AuthAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AuthAccountRepository extends JpaRepository<AuthAccount, String> {
  Optional<AuthAccount> findFirstByUserId(String userId);

  boolean existsByUserId(String userId);
  boolean existsByUsername(String username);

  @Transactional
  @Modifying
  @Query(
      value = "UPDATE account INNER JOIN user ON account.user_id = user.id SET account.is_activated = true WHERE user.email = :email",
      nativeQuery = true
  )
  void activeAccountByEmail(@Param("email") String email);
}
