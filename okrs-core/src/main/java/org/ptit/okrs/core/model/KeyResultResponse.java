package org.ptit.okrs.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.entity.KeyResult;
import org.ptit.okrs.core_util.DateUtils;

@Data
@NoArgsConstructor
public class KeyResultResponse {
  private String id;
  private String objectiveId;
  private String title;
  private String description;
  private int startDate;
  private int endDate;
  private int progress;

  public static KeyResultResponse from(KeyResult keyResult) {
    KeyResultResponse response = new KeyResultResponse();
    response.setId(keyResult.getId());
    response.setObjectiveId(keyResult.getObjectiveId());
    response.setTitle(keyResult.getTitle());
    response.setDescription(keyResult.getDescription());
    response.setStartDate(DateUtils.getDate(keyResult.getStartDate()));
    response.setEndDate(DateUtils.getDate(keyResult.getEndDate()));
    response.setProgress(keyResult.getProgress());
    return response;
  }
}
