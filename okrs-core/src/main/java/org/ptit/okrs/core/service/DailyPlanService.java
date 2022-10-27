package org.ptit.okrs.core.service;

import java.util.List;
import org.ptit.okrs.core.constant.DailyPlanStatus;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.service.base.BaseService;

public interface DailyPlanService extends BaseService<DailyPlan> {
  /**
   * Create new task in daily plan
   * @param title       - title of the task
   * @param description - description of the task
   * @param userId      - id of the current user who logged in our system
   * @param keyResultId - the id of the key-result that the task will be linked.
   * @return task of the daily plan
   */
  DailyPlanResponse create(
      String title,
      String description,
      String userId,
      String keyResultId);

  /**
   * Delete daily plan
   * @param id - id of the daily plan you want to delete
   */
  void delete(String id);

  /**
   * Link daily plan to key results
   * @param id - the id of the daily plan
   * @param keyResultId - id of the key results want to link
   * @return - The information of the daily plan has been linked
   */
  DailyPlanResponse linkDailyPlanToKeyResults(String id, String keyResultId);

  /**
   * Update daily plan
   *
   * @param id          - id of the daily plan want to update
   * @param title       - title of the task
   * @param description - description of the task
   * @param date        - date of the task
   * @param note        - note of the task
   * @param userId      - userId of the task
   * @param keyResultId - keyResultId of the task
   * @return - information of daily plan after update
   */
  DailyPlanResponse update(
      String id,
      String title,
      String description,
      Integer date,
      String note,
      String userId,
      String keyResultId);

  /**
   * Update status daily plan
   * @param id      - id of the dail plan want to update status
   * @param status  - status of the task
   * @return - information of daily plan after updating status
   */
  DailyPlanResponse updateStatusDailyPlan(String id, DailyPlanStatus status);

}
