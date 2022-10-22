package org.ptit.okrs.core.service.impl;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.DailyPlanStatus;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.repository.DailyPlanRepository;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
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
  public DailyPlanResponse linkDailyPlanToKeyResults(String id, String keyResultId) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public DailyPlanResponse update(String id, String title, String description, Integer date,
      String note, String userId, String keyResultId) {
    log.info("(update)id: {}, title: {}", id, title);
    DailyPlan dailyPlanCheck =
        repository
            .findById(id)
            .orElseThrow(
                () -> {
                  try {
                    throw new NotFoundException();
                  } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                  }
                });
    dailyPlanCheck.setTitle(title);
    dailyPlanCheck.setDescription(description);
    dailyPlanCheck.setDate(date);
    dailyPlanCheck.setNote(note);
    dailyPlanCheck.setUserId(userId);
    dailyPlanCheck.setKeyResultId(keyResultId);
    DailyPlan update = repository.save(dailyPlanCheck);
    return DailyPlanResponse.from(update);
  }

  @Override
  public DailyPlanResponse updateStatusDailyPlan(String id, DailyPlanStatus status) {
    log.info("(updateStatusDailyPlan)id: {}, status: {}", id, status);
    DailyPlan dailyPlanCheck =
        repository
            .findById(id)
            .orElseThrow(
                () -> {
                  try {
                    throw new NotFoundException();
                  } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                  }
                });
    dailyPlanCheck.setStatus(status);
    DailyPlan update = repository.save(dailyPlanCheck);
    return DailyPlanResponse.from(update);
  }
}
