package org.ptit.okrs.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.DailyPlanStatus;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.exception.ConflicDataTitleOnDateDailyPlan;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.repository.DailyPlanRepository;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.NotFoundException;
import org.ptit.okrs.core_util.DateUtils;
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
  @Transactional
  public DailyPlanResponse create(
      String title,
      String description,
      String userId,
      String keyResultId
  ) {
    log.info("(create)title: {}, description: {}, userId: {}, keyResultId: {}",
        title, description, userId, keyResultId);
    if (repository.existsByTitleAndDate(title, DateUtils.getCurrentDateInteger())) {
      log.error("(create)title: {}, date:{} --> Error: Title on date daily plan is already taken!", title, DateUtils.getCurrentDateInteger());
      throw new ConflicDataTitleOnDateDailyPlan(title, DateUtils.getCurrentDateInteger());
    }
    try {
      return DailyPlanResponse.from(
          create(DailyPlan.of(title, description, userId, keyResultId)));
    } catch (DuplicateKeyException er) {
      log.error("(create)exception : {}", er.getClass().getName());
      //TODO: Build response in message for this exception
      throw new DuplicateKeyException(DUPLICATE_KEY_MESSAGE);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<DailyPlanResponse> getByKeyResultId(String keyResultId) {
    //TODO: Get by key result and userId
    log.info("(getByKeyResultId)keyResultId: {}", keyResultId);
    return repository.findByKeyResultId(keyResultId)
        .stream()
        .map(DailyPlanResponse :: from)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<DailyPlanResponse> getByDate(Integer date) {
    //TODO: get by date and userId
    log.info("(getByDate) date: {}", date);
    return repository.findByDate(date)
        .stream()
        .map(DailyPlanResponse :: from)
        .collect(Collectors.toList());

  }

  @Override
  @Transactional
  public DailyPlanResponse linkDailyPlanToKeyResults(String id, String keyResultId) {
    //TODO: check permission: user only update their daily plan
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
  @Transactional
  public Boolean existsById(String keyResultId) {
    log.info("(existsById)id : {}", keyResultId);
    return repository.existsById(keyResultId);
  }

  @Override
  @Transactional
  public DailyPlanResponse update(
      String id,
      String title,
      String description,
      Integer date,
      String note,
      String keyResultId
  ) {
    log.info("(update)id: {}, title: {}", id, title);
    if (repository.existsByTitleAndDate(title, date)) {
      //TODO: check permission: user only update their daily plan
      log.error("(update)title: {}, date: {} --> Error: Title on date daily plan is already taken!", title, date);
      throw new ConflicDataTitleOnDateDailyPlan(title, DateUtils.getCurrentDateInteger());
    }
    DailyPlan dailyPlanCheck =
        repository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NotFoundException(id, DailyPlan.class.getSimpleName());
                });
    dailyPlanCheck.setTitle(title);
    dailyPlanCheck.setDescription(description);
    dailyPlanCheck.setDate(DateUtils.getDate(date));
    dailyPlanCheck.setNote(note);
    dailyPlanCheck.setKeyResultId(keyResultId);
    DailyPlan update = update(dailyPlanCheck);
    return DailyPlanResponse.from(update);
  }

  @Override
  @Transactional
  public DailyPlanResponse updateStatusDailyPlan(String id, DailyPlanStatus status) {
    log.info("(updateStatusDailyPlan)id: {}, status: {}", id, status);

    if(!isExist(id)) {
      log.error("(updateStatusDailyPlan)id: {} not found", id);
      throw new NotFoundException(id, DailyPlan.class.getSimpleName());
    }

    //TODO: write query for update this daily plan. Not update all entity.
    var dailyPlan = find(id);
    dailyPlan.setStatus(status);
    DailyPlan update = update(dailyPlan);
    return DailyPlanResponse.from(update);
  }
}
