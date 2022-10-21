package org.ptit.okrs.core.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ObjectiveDetailResponse extends ObjectiveResponse {
  private List<KeyResultResponse> keyResults;
}
