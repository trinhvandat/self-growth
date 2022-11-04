package org.ptit.okrs.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core.repository.projection.ObjectiveProjection;
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
  private String objectType;

  public static ObjectiveResponse from(Objective objective) {
    return getObjectiveResponse(objective.getId(), objective.getTitle(), objective.getDescription(),
        objective.getStartDate(), objective.getEndDate(), objective.getType(),
        objective.getTimePeriodType());
  }

  public static ObjectiveResponse from(ObjectiveProjection objective) {
    return getObjectiveResponse(objective.getId(), objective.getTitle(), objective.getDescription(),
        objective.getStartDate(), objective.getEndDate(), objective.getType(),
        objective.getTimePeriodType());
  }

  private static ObjectiveResponse getObjectiveResponse(String id, String title, String description,
      Long startDate, Long endDate, OkrsType type, OkrsTimeType timePeriodType) {
    ObjectiveResponse response = new ObjectiveResponse();
    response.setId(id);
    response.setTitle(title);
    response.setDescription(description);
    response.setStartDate(DateUtils.getDate(startDate));
    response.setEndDate(DateUtils.getDate(endDate));
    response.setType(type);
    response.setTimePeriodType(timePeriodType);
    response.setObjectType(Objective.class.getSimpleName());
    return response;
  }
}
