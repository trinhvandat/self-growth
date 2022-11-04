package org.ptit.okrs.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core_util.DateUtils;

@AllArgsConstructor(staticName = "of")
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
  private String objectType;

  public static ObjectiveResponse from(Objective objective) {
    return ObjectiveResponse.of(
        objective.getId(),
        objective.getTitle(),
        objective.getDescription(),
        DateUtils.getDate(objective.getStartDate()),
        DateUtils.getDate(objective.getEndDate()),
        objective.getType(),
        objective.getTimePeriodType(),
        Objective.class.getSimpleName()
    );
  }
}
