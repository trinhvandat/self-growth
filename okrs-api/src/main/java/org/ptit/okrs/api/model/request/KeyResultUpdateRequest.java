package org.ptit.okrs.api.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.exception.PathVariableException;

@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class KeyResultUpdateRequest extends KeyResultCreateRequest {
  @NotBlank private String id;

  public void validatePathVariable(String id, String objectiveId) {
    super.validatePathVariable(objectiveId);
    if (!this.id.equals(id)) {
      log.error("id : {} --> PathVariableException", id);
      throw new PathVariableException(this.id, id);
    }
  }
}
