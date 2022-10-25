package org.ptit.okrs.core.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectiveProjection {

  private String id;
  private String title;
  private String description;
  private Long startDate;
  private Long endDate;
  private OkrsType type;
  private OkrsTimeType timePeriodType;
  private String userId;
}
