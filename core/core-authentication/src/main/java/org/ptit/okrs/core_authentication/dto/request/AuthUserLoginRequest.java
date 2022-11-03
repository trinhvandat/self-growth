package org.ptit.okrs.core_authentication.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthUserLoginRequest {

  //TODO: AnhNHS --> validate username not have specific character
  @NotBlank
  private String username;

  @NotBlank
  private String password;
}
