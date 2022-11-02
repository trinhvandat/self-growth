package org.ptit.okrs.core_authentication.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity(name = "AuthUser")
@NoArgsConstructor
@Table(name = "user")
public class AuthUser extends BaseEntity {
  private String name;
  private String phone;
  private String email;
  private Integer dateOfBirth;
  private String gender;
  private String address;
  private String avatar;

  public static AuthUser from(String name, String email) {
    var user = new AuthUser();
    user.setName(name);
    user.setEmail(email);
    return user;
  }
}
