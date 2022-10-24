package org.ptit.okrs.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.Gender;
import org.ptit.okrs.core.entity.User;

@Data
@NoArgsConstructor
public class UserResponse {
  private String id;
  private String name;
  private Gender gender;
  private String address;
  private String avatar;
  private Long dateOfBirth;

  public UserResponse from(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.gender = user.getGender();
    this.address = user.getAddress();
    this.avatar = user.getAvatar();
    this.dateOfBirth = user.getDateOfBirth();
    return this;
  }
}
