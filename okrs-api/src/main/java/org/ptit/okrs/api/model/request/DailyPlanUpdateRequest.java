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
}
