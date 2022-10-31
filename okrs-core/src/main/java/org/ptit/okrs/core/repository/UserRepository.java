package org.ptit.okrs.core.repository;

import java.util.Optional;
import org.ptit.okrs.core.entity.User;
import org.ptit.okrs.core.model.UserResponse;
import org.ptit.okrs.core.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {

  @Query("select new org.ptit.okrs.core.model.UserResponse(u.id, u.name, u.phone, u.gender, u.address, u.email, avatar, u.dateOfBirth) from User u where u.id = :id ")
  Optional<UserResponse> find(String id);

  boolean existsByEmail(String email);

  @Query("Select u.avatar from User u where u.id = :userId")
  String findAvatar(@Param("userId") String userId);
}
