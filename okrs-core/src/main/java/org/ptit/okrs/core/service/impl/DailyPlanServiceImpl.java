package org.ptit.okrs.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.repository.DailyPlanRepository;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class DailyPlanServiceImpl extends BaseServiceImpl<DailyPlan> implements DailyPlanService {
  private final DailyPlanRepository repository;

  public static final String DUPLICATE_KEY_MESSAGE = "Not be able to perform tasks at the same time";

  public DailyPlanServiceImpl(DailyPlanRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  @Transactional(readOnly = true)
  public DailyPlanResponse create(String title, String description, String userId, String keyResultId) {
    log.info("(create)title: {}", title);
    try {
      return DailyPlanResponse.from(repository.save(title, description, userId, keyResultId));
    } catch (DuplicateKeyException er) {
      log.error("(create)exception : {}", er.getClass().getName());
      throw new DuplicateKeyException(DUPLICATE_KEY_MESSAGE);
    }
  }

  @Override
  public void deleteById(String id) {

  }

  @Override
  public DailyPlanResponse linkDailyPlanToKeyResults(String keyResultId) {
    return null;
  }

  @Override
  public DailyPlanResponse update(String id, String title, String description, Integer date,
      String note, String userId) {
    return null;
  }

  @Override
  public DailyPlanResponse updateStatusDailyPlan(String Id) {
    return null;
  }
}
