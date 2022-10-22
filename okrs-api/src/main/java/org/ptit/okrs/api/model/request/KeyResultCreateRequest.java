package org.ptit.okrs.api.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core_exception.BadRequestException;
import org.ptit.okrs.core_util.ValidationUtils;

@Data
@NoArgsConstructor
public class KeyResultCreateRequest {
  @NotBlank private String objectiveId;
  @NotBlank private String title;
  private String description;
  @NotNull private Integer startDate;
  @NotNull private Integer endDate;

  @Min(0)
  @Max(100)
  private Integer progress;

  public void validate() {
    if (!ValidationUtils.validateStartDateAndEndDate(startDate, endDate)) {
      //TODO: AnhNHS add specific exception
      throw new BadRequestException();
    }
  }
}
