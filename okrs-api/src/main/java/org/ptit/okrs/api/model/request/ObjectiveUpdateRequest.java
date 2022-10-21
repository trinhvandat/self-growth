package org.ptit.okrs.api.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ptit.okrs.api.model.request.ObjectiveCreateRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class ObjectiveUpdateRequest extends ObjectiveCreateRequest {
  @NotBlank private String id;
}
