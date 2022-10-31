package org.ptit.okrs.core.repository;

import java.util.List;
import java.util.Optional;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core.repository.base.BaseRepository;
import org.ptit.okrs.core.repository.projection.ObjectiveProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectiveRepository extends BaseRepository<Objective> {
  @Query(
      "select new org.ptit.okrs.core.repository.projection.ObjectiveProjection(o.id,"
          + " o.title, "
          + " o.description,"
          + " o.startDate,"
          + " o.endDate,"
          + " o.type,"
          + " o.timePeriodType,"
          + " o.userId) from Objective o where o.userId = :userId")
  List<ObjectiveProjection> findByUserId(@Param("userId") String userId);

  @Query(
      "select new org.ptit.okrs.core.repository.projection.ObjectiveProjection(o.id,"
          + " o.title, "
          + " o.description,"
          + " o.startDate,"
          + " o.endDate,"
          + " o.type,"
          + " o.timePeriodType,"
          + " o.userId) from Objective o where o.id = :id")
  Optional<ObjectiveProjection> find(@Param("id") String id);
}
