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
  private String email;
  private String avatar;
  private Long dateOfBirth;

  public static UserResponse from(User user) {
    var userResponse = new UserResponse();
    userResponse.id = user.getId();
    userResponse.name = user.getName();
    userResponse.gender = user.getGender();
    userResponse.email = user.getEmail();
    userResponse.address = user.getAddress();
    userResponse.avatar = user.getAvatar();
    userResponse.dateOfBirth = user.getDateOfBirth();
    return userResponse;
  }
}
