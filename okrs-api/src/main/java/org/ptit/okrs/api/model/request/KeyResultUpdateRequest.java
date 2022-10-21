package org.ptit.okrs.api.model.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ptit.okrs.api.model.request.KeyResultCreateRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class KeyResultUpdateRequest extends KeyResultCreateRequest {
  @NotBlank private String id;
}
