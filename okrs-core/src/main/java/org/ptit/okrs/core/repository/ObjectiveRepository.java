package org.ptit.okrs.core.repository;

import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjectiveRepository extends BaseRepository<Objective> {
  Optional<Objective> findByUserId(String userId);
}
