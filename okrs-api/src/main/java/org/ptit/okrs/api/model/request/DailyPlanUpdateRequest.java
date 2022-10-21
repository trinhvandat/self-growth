package org.ptit.okrs.api.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DailyPlanUpdateRequest extends DailyPlanCreateRequest {
  @NotNull
  private Integer date;
  private String note;
}
