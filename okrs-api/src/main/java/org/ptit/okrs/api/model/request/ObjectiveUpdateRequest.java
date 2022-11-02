package org.ptit.okrs.api.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ptit.okrs.api.exception.PathVariableException;

@Data
@EqualsAndHashCode(callSuper = true)
public class ObjectiveUpdateRequest extends ObjectiveCreateRequest {
  @NotBlank private String id;

  public void validatePathVariable(String id) {
    if (!this.id.equals(id)) {
      throw new PathVariableException(this.id, id);
    }
  }
}
