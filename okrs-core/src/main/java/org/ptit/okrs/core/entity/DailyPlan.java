package org.ptit.okrs.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.constant.DailyPlanStatus;
import org.ptit.okrs.core.entity.base.BaseEntityWithUpdater;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Table(name = "daily_plan")
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyPlan extends BaseEntityWithUpdater {

  @Column(nullable = false)
  private String title;

  private String description;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private DailyPlanStatus status;

  private Integer date;

  private String userId;

  private String note;

  private String keyResultId;
}
