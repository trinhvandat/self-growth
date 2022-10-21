package org.ptit.okrs.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.DailyPlanStatus;
import org.ptit.okrs.core.entity.DailyPlan;

@Data
@NoArgsConstructor
public class DailyPlanResponse {
  private String id;
  private String title;
  private String description;
  private DailyPlanStatus status;
  private Integer date;
  private String note;
  private String keyResultId;

  public static DailyPlanResponse from(DailyPlan dailyPlan) {
    DailyPlanResponse response = new DailyPlanResponse();
    response.setTitle(dailyPlan.getTitle());
    response.setDescription(dailyPlan.getDescription());
    response.setStatus(dailyPlan.getStatus());
    response.setDate(dailyPlan.getDate());
    response.setNote(dailyPlan.getNote());
    response.setKeyResultId(dailyPlan.getKeyResultId());
    return response;
  }
}
