package org.ptit.okrs.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;

@Data
@NoArgsConstructor
public class ObjectiveResponse {
  private String id;
  private String title;
  private String description;
  private int startDate;
  private int endDate;
  private OkrsType type;
  private OkrsTimeType timePeriodType;
}
