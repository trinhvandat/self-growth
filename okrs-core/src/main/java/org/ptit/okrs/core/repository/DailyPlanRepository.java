package org.ptit.okrs.core.repository;

import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyPlanRepository extends BaseRepository<DailyPlan> {

  DailyPlan save(String title, String description, String userId, String keyResultId);
}
