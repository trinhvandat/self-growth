package org.ptit.okrs.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core_util.DateUtils;

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

  public static ObjectiveResponse from(Objective objective) {
    ObjectiveResponse response = new ObjectiveResponse();
    response.setId(objective.getId());
    response.setTitle(objective.getTitle());
    response.setDescription(objective.getDescription());
    response.setStartDate(DateUtils.getDate(objective.getStartDate()));
    response.setEndDate(DateUtils.getDate(objective.getEndDate()));
    response.setType(objective.getType());
    response.setTimePeriodType(objective.getTimePeriodType());
    return response;
  }
}
