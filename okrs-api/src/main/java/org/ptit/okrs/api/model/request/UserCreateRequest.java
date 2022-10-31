package org.ptit.okrs.api.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateRequest {
  @NotBlank
  private String name;
  @Email
  @NotBlank
  private String email;
}
