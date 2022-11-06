package org.ptit.okrs.core.repository;

import java.util.List;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjectiveRepository extends BaseRepository<Objective> {
  List<Objective> findByUserId(String userId);

  Optional<Objective> findByIdAndUserId(String id, String userId);
}
