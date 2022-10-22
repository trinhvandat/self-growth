package org.ptit.okrs.core.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.entity.base.BaseEntityWithUpdater;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@Table(name = "objective")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Objective extends BaseEntityWithUpdater {

  @Column(nullable = false)
  private String title;

  private String description;

  @Column(nullable = false)
  private Long startDate;

  private Long endDate;

  @Enumerated(EnumType.STRING)
  private OkrsType type;

  @Enumerated(EnumType.STRING)
  private OkrsTimeType timePeriodType;

  @Column(nullable = false)
  private String userId;

  public static Objective of(
      String title,
      String description,
      Long startDate,
      Long endDate,
      OkrsType type,
      OkrsTimeType timePeriodType,
      String userId) {
    Objective objective = new Objective();
    objective.setTitle(title);
    objective.setDescription(description);
    objective.setStartDate(startDate);
    objective.setEndDate(endDate);
    objective.setType(type);
    objective.setTimePeriodType(timePeriodType);
    objective.setUserId(userId);
    return objective;
  }
}
