package org.ptit.okrs.api.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import org.ptit.okrs.core.entity.DailyPlan;

@Data
@NoArgsConstructor
public class DailyPlanUpdateRequest extends DailyPlanCreateRequest {
  @NotNull
  private Integer date;
  private String note;
  private String userId;

  public DailyPlan toDailyPlanm() {
    DailyPlan dailyPlan = new DailyPlan();
    dailyPlan.setTitle(this.getTitle());
    dailyPlan.setDescription(this.getDescription());
    dailyPlan.setKeyResultId(this.getKeyResultId());
    dailyPlan.setDate(this.getDate());
    dailyPlan.setNote(this.getNote());
    dailyPlan.setUserId(this.getUserId());
    return dailyPlan;
  }
}
