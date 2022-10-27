package org.ptit.okrs.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.DailyPlanStatus;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.repository.DailyPlanRepository;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.NotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class DailyPlanServiceImpl extends BaseServiceImpl<DailyPlan> implements DailyPlanService {

  public static final String DUPLICATE_KEY_MESSAGE = "Not be able to perform tasks at the same time";
  private final DailyPlanRepository repository;

  public DailyPlanServiceImpl(DailyPlanRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  @Transactional(readOnly = true)
  public DailyPlanResponse create(String title, String description, String userId,
      String keyResultId) {
    log.info("(create)title: {}", title);
    try {
      return DailyPlanResponse.from(
          create(DailyPlan.of(title, description, userId, keyResultId)));
    } catch (DuplicateKeyException er) {
      log.error("(create)exception : {}", er.getClass().getName());
      throw new DuplicateKeyException(DUPLICATE_KEY_MESSAGE);
    }
  }

  @Override
  public void delete(String id) {
    super.delete(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<DailyPlanResponse> getByKeyResultId(String keyResultId) {
    log.info("(getByKeyResultId)keyResultId: {}", keyResultId);
    return repository.findByKeyResultId(keyResultId)
        .stream()
        .map(DailyPlanResponse :: from)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<DailyPlanResponse> getByDate(Integer date) {
    log.info("(getByDate) date: {}", date);
    return repository.findByDate(date)
        .stream()
        .map(DailyPlanResponse :: from)
        .collect(Collectors.toList());

  }

  @Override
  @Transactional(readOnly = true)
  public DailyPlanResponse linkDailyPlanToKeyResults(String id, String keyResultId) {
    log.info("(linkDailyPlanToKeyResults)id: {}, keyResultId: {}", id, keyResultId);
    DailyPlan dailyPlanCheck =
        repository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NotFoundException(id, DailyPlan.class.getSimpleName());
                });
    dailyPlanCheck.setKeyResultId(keyResultId);
    DailyPlan update = update(dailyPlanCheck);
    return DailyPlanResponse.from(update);
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
                  throw new NotFoundException(id, DailyPlan.class.getSimpleName());
                });
    dailyPlanCheck.setTitle(title);
    dailyPlanCheck.setDescription(description);
    dailyPlanCheck.setDate(date);
    dailyPlanCheck.setNote(note);
    dailyPlanCheck.setUserId(userId);
    dailyPlanCheck.setKeyResultId(keyResultId);
    DailyPlan update = update(dailyPlanCheck);
    return DailyPlanResponse.from(update);
  }

  @Override
  @Transactional(readOnly = true)
  public DailyPlanResponse updateStatusDailyPlan(String id, DailyPlanStatus status) {
    log.info("(updateStatusDailyPlan)id: {}, status: {}", id, status);
    DailyPlan dailyPlanCheck =
        repository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NotFoundException(id, DailyPlan.class.getSimpleName());
                });
    dailyPlanCheck.setStatus(status);
    DailyPlan update = update(dailyPlanCheck);
    return DailyPlanResponse.from(update);
  }
}
