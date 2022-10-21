package org.ptit.okrs.api.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;

@Data
@NoArgsConstructor
public class ObjectiveCreateRequest {
  @NotBlank private String title;
  private String description;
  @NotNull private Integer startDate;
  @NotNull private Integer endDate;
  private OkrsType type;
  private OkrsTimeType timePeriodType;
}
