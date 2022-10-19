package org.ptit.okrs.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.DailyPlanStatus;

@Data
@NoArgsConstructor
public class DailyPlanResponse {
  private String id;
  private String title;
  private String description;
  private DailyPlanStatus status;
  private Integer date;
  private String userId;
  private String note;
  private String keyResultId;
}
