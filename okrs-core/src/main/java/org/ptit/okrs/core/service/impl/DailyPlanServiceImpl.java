package org.ptit.okrs.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.repository.DailyPlanRepository;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;

@Slf4j
public class DailyPlanServiceImpl extends BaseServiceImpl<DailyPlan> implements DailyPlanService {
  private final DailyPlanRepository repository;

  public DailyPlanServiceImpl(DailyPlanRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public DailyPlanResponse create(String title, String description, String userId, String keyResultId) {
    return null;
  }
}
