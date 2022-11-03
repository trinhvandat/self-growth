package org.ptit.okrs.core_authentication.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.exception.PasswordConfirmNotMatchException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Data
@NoArgsConstructor
@Slf4j
public class AuthUserRegisterRequest {
  @NotBlank
  @Email
  private String email;

  //TODO: AnhNHS validate not have specific character
  @NotBlank
  private String username;

  @NotBlank
  @Size(min = 8)
  private String password;

  @NotBlank
  @Size(min = 8)
  private String passwordConfirm;

  public void validate() {
    if (!Objects.equals(password, passwordConfirm)) {
      log.error("(validate)password: {}, passwordConfirm: {} not equal", password, passwordConfirm);
      throw new PasswordConfirmNotMatchException();
    }
  }
}
