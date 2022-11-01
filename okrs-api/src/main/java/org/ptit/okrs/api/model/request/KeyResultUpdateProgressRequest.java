package org.ptit.okrs.api.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.exception.PathVariableException;

@Data
@NoArgsConstructor
@Slf4j
public class KeyResultUpdateProgressRequest {
  @NotBlank private String id;

  @Min(0)
  @Max(100)
  private Integer progress;

  public void validatePathVariable(String id) {
    if (!this.id.equals(id)) {
      log.error("id : {} --> PathVariableException", id);
      throw new PathVariableException(this.id, id);
    }
  }
}
