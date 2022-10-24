package org.ptit.okrs.api.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.Gender;

@Data
@NoArgsConstructor
public class UserUpdateRequest {
  @NotBlank
  private String name;
  private String phone;
  @Email
  @NotBlank
  private String email;
  private Long dateOfBirth;
  private Gender gender;
  private String address;
}
