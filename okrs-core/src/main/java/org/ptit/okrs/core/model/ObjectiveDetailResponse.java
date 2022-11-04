package org.ptit.okrs.core.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core_util.DateUtils;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ObjectiveDetailResponse extends ObjectiveResponse {

  private List<KeyResultResponse> keyResults;

  public static ObjectiveDetailResponse from(
      Objective objective, List<KeyResultResponse> keyResults) {
    var response = new ObjectiveDetailResponse();
    response.setId(objective.getId());
    response.setTitle(objective.getTitle());
    response.setDescription(objective.getDescription());
    response.setStartDate(DateUtils.getDate(objective.getStartDate()));
    response.setEndDate(DateUtils.getDate(objective.getEndDate()));
    response.setType(objective.getType());
    response.setTimePeriodType(objective.getTimePeriodType());
    response.setKeyResults(keyResults);
    response.setObjectType(Objective.class.getSimpleName());
    return response;
  }
}
