package org.ptit.okrs.core_authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor(staticName = "of")
@Entity
@Data
@NoArgsConstructor
@Table(name = "account")
public class AuthAccount extends BaseEntity {
  private String username;
  private String password;
  private Boolean isActivated = false;
  private String userId;

  public static AuthAccount of(String userId, String username, String password) {
    return AuthAccount.of(username, password, false, userId);
  }
}
