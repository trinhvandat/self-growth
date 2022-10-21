package org.ptit.okrs.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

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
}
