package org.ptit.okrs.core_authentication.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthUserResetPasswordRequest {

  //Email
  @NotBlank
  private String email;
}
