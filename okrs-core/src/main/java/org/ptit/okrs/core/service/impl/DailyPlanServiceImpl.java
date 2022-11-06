package org.ptit.okrs.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.DailyPlanStatus;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.exception.DuplicateKeyException;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.repository.DailyPlanRepository;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.UserService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.ConflictException;
import org.ptit.okrs.core_exception.ForbiddenException;
import org.ptit.okrs.core_exception.NotFoundException;
import org.ptit.okrs.core_util.DateUtils;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class DailyPlanServiceImpl extends BaseServiceImpl<DailyPlan> implements DailyPlanService {

  public static final String DUPLICATE_KEY_MESSAGE = "Not be able to perform tasks at the same time";
  private final DailyPlanRepository repository;
  private final UserService userService;

  public DailyPlanServiceImpl(DailyPlanRepository repository, UserService userService) {
    super(repository);
    this.repository = repository;
    this.userService = userService;
  }

  @Override
  @Transactional
  public DailyPlanResponse create(
      String title,
      String description,
      String userId,
      String keyResultId
  ) {
    log.info("(create)title: {}, description: {}, userId: {}, keyResultId: {}",
        title,
        description,
        userId,
        keyResultId);
    if (repository.existsByTitleAndDate(title, DateUtils.getEpochTime(DateUtils.getCurrentDateInteger()))) {
      log.error("(create)title: {}, date:{} --> Error: Title on date daily plan is already taken!", title, DateUtils.getEpochTime(DateUtils.getCurrentDateInteger()));
      throw new ConflictException(title, DateUtils.getEpochTime(DateUtils.getCurrentDateInteger()));
    }
    try {
      return DailyPlanResponse.from(
          create(DailyPlan.of(title, description, userId, keyResultId)));
    } catch (DuplicateKeyException er) {
      log.error("(create)exception : {}", er.getClass().getName());
      throw new DuplicateKeyException(DUPLICATE_KEY_MESSAGE);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<DailyPlanResponse> getByKeyResultId(String keyResultId, String userId) {
    log.info("(getByKeyResultId)keyResultId: {}", keyResultId);
    return repository.findByKeyResultIdAndUserId(keyResultId, userId)
        .stream()
        .map(DailyPlanResponse :: from)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<DailyPlanResponse> getByDate(Integer date, String userId) {
    log.info("(getByDate) date: {}", date);
    return repository.findByDateAndUserId(DateUtils.getEpochTime(date), userId)
        .stream()
        .map(DailyPlanResponse :: from)
        .collect(Collectors.toList());

  }

  @Override
  @Transactional
  public DailyPlanResponse linkDailyPlanToKeyResults(String id, String keyResultId, String userId) {
    log.info("(linkDailyPlanToKeyResults)id: {}, keyResultId: {}", id, keyResultId);
    DailyPlan dailyPlanCheck = find(id);
    if (!dailyPlanCheck.getUserId().equals(userId)) {
      log.error("(update)userId : {} --> FORBIDDEN EXCEPTION", userId);
      throw new ForbiddenException(userId);
    }
    dailyPlanCheck.setKeyResultId(keyResultId);
    DailyPlan update = update(dailyPlanCheck);
    return DailyPlanResponse.from(update);
  }

  @Override
  @Transactional
  public DailyPlanResponse update(
      String id,
      String title,
      String description,
      Integer date,
      String note,
      String keyResultId,
      String userId
  ) {
    log.info("(update)id: {}, title: {}, description: {}, date:{}, note: {}, keyResultId: {}, userId: {}",
        id,
        title,
        description,
        date,
        note,
        keyResultId,
        userId);
    if (repository.existsByTitleAndDate(title, DateUtils.getEpochTime(date))) {
      log.error("(update)title: {}, date: {} --> Error: Title on date daily plan is already taken!", title, date);
      throw new ConflictException(title, DateUtils.getEpochTime(date));
    }
    var dailyPlanCheck = find(id);
    if (!dailyPlanCheck.getUserId().equals(userId)) {
      log.error("(update)userId : {} --> FORBIDDEN EXCEPTION", userId);
      throw new ForbiddenException(userId);
    }
    dailyPlanCheck.setTitle(title);
    dailyPlanCheck.setDescription(description);
    dailyPlanCheck.setDate(DateUtils.getEpochTime(date));
    dailyPlanCheck.setNote(note);
    dailyPlanCheck.setKeyResultId(keyResultId);
    DailyPlan update = update(dailyPlanCheck);
    return DailyPlanResponse.from(update);
  }

  @Override
  @Transactional
  public void updateStatusDailyPlan(String id, DailyPlanStatus status) {
    log.info("(updateStatusDailyPlan)id: {}, status: {}", id, status);

    if(!isExist(id)) {
      log.error("(updateStatusDailyPlan)id: {} not found", id);
      throw new NotFoundException(id, DailyPlan.class.getSimpleName());
    }

    repository.updateStatus(id, status);
  }
}
