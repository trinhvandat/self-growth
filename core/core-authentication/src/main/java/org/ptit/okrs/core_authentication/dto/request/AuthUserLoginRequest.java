package org.ptit.okrs.core_authentication.dto.request;

import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthUserLoginRequest {

  @NotBlank
  @Pattern(regexp = "[A-Za-z0-9]+")
  private String username;

  @NotBlank
  private String password;
}
