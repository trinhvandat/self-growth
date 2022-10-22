package org.ptit.okrs.api.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.exception.OkrsDateInvalidException;
import org.ptit.okrs.api.exception.UnknownEnumException;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;

@Data
@NoArgsConstructor
@Slf4j
public class ObjectiveCreateRequest {
  @NotBlank private String title;
  private String description;
  @NotNull private Integer startDate;
  @NotNull private Integer endDate;
  private OkrsType type;
  private OkrsTimeType timePeriodType;

  public void validate() {
    if (this.startDate > this.endDate) {
      log.error("(validate)startDate : {}, endDate : {}", startDate, endDate);
      throw new OkrsDateInvalidException(
          "objective", String.valueOf(startDate), String.valueOf(endDate));
    }
    if (!OkrsType.contains(type.name())) {
      log.error("(validate)type : {}", type);
      throw new UnknownEnumException("okrsType", type.name());
    }
    if (!OkrsTimeType.contains(timePeriodType.name())) {
      log.error("(validate)timePeriodType : {}", timePeriodType);
      throw new UnknownEnumException("timePeriodType", timePeriodType.name());
    }
  }
}
