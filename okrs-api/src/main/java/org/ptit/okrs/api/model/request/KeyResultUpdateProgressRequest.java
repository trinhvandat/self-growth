package org.ptit.okrs.api.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KeyResultUpdateProgressRequest {
  @NotBlank private String id;
  @NotBlank private String objectiveId;
  @Min(0)
  @Max(100)
  private Integer progress;
}
