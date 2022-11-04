package org.ptit.okrs.core.repository;

import java.util.List;
import org.ptit.okrs.core.entity.KeyResult;
import org.ptit.okrs.core.repository.base.BaseRepository;
import org.ptit.okrs.core.repository.projection.KeyResultProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyResultRepository extends BaseRepository<KeyResult> {

  void deleteAllByObjectiveId(String objectiveId);
  Boolean existsByTitleAndObjectiveId(String title, String objectiveId);
  @Query("select new org.ptit.okrs.core.repository.projection.KeyResultProjection(kr.id,"
      + " kr.objectiveId,"
      + " kr.title,"
      + " kr.description,"
      + " kr.startDate,"
      + " kr.endDate,"
      + " kr.progress,"
      + " kr.userId) from KeyResult kr where kr.objectiveId = :objectiveId")
  List<KeyResultProjection> find(@Param("objectiveId")String objectiveId);
}
