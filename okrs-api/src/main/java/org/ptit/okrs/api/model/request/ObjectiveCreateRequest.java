package org.ptit.okrs.api.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core.exception.OkrsDateInvalidException;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.validation.ValidDateInteger;
import org.ptit.okrs.core_util.ValidationUtils;

@Data
@NoArgsConstructor
@Slf4j
public class ObjectiveCreateRequest {
  @NotBlank private String title;
  private String description;
  @NotNull @ValidDateInteger private Integer startDate;
  @NotNull @ValidDateInteger private Integer endDate;
  private OkrsType type;
  private OkrsTimeType timePeriodType;

  public void validate() {
    if (!ValidationUtils.validateStartDateAndEndDate(startDate, endDate)) {
      log.error("(validate)startDate : {}, endDate : {}", startDate, endDate);
      throw new OkrsDateInvalidException(
          Objective.class.getSimpleName(), String.valueOf(startDate), String.valueOf(endDate));
    }
  }
}
