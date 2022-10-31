package org.ptit.okrs.core.repository;

import org.ptit.okrs.core.entity.User;
import org.ptit.okrs.core.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {
  boolean existsByEmail(String email);
}
