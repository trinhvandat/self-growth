package org.ptit.okrs.api.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import org.ptit.okrs.core.entity.DailyPlan;

@Data
@NoArgsConstructor
public class DailyPlanCreateRequest {
  @NotBlank
  private String title;

  private String description;

  private String keyResultId;

  public DailyPlan toDailyPlanm() {
    DailyPlan dailyPlan = new DailyPlan();
    dailyPlan.setTitle(this.getTitle());
    dailyPlan.setDescription(this.getDescription());
    dailyPlan.setKeyResultId(this.getKeyResultId());
    return dailyPlan;
  }
}