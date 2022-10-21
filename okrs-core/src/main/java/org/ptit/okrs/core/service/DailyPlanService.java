package org.ptit.okrs.core.service;

import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.service.base.BaseService;

public interface DailyPlanService extends BaseService<DailyPlan> {
  /**
   * Create new task in daily plan
   * @param title - title of the task
   * @param description - description of the task
   * @param userId - id of the current user who logged in our system
   * @param keyResultId - the id of the key-result that the task will be linked.
   * @return task of the daily plan
   */
  DailyPlanResponse create(String title, String description, String userId, String keyResultId);
}
