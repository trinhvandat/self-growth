package org.ptit.okrs.core.repository;

import java.util.List;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyPlanRepository extends BaseRepository<DailyPlan> {

  List<DailyPlan> findByDate(Integer date);

  List<DailyPlan> findByKeyResultId(String keyResultId);

}
