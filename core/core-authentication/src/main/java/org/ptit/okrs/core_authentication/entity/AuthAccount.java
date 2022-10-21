package org.ptit.okrs.core_authentication.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "account")
public class AuthAccount extends BaseEntity {
}
