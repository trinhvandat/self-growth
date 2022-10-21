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

}
