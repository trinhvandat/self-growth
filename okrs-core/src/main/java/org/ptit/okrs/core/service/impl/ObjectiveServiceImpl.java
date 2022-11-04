package org.ptit.okrs.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core.exception.OkrsKeyResultPeriodTimeException;
import org.ptit.okrs.core.model.ObjectiveDetailResponse;
import org.ptit.okrs.core.model.ObjectiveResponse;
import org.ptit.okrs.core.repository.ObjectiveRepository;
import org.ptit.okrs.core.service.KeyResultService;
import org.ptit.okrs.core.service.ObjectiveService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.ForbiddenException;
import org.ptit.okrs.core_exception.NotFoundException;
import org.ptit.okrs.core_util.DateUtils;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class ObjectiveServiceImpl extends BaseServiceImpl<Objective> implements ObjectiveService {

  private final ObjectiveRepository repository;
  private final KeyResultService keyResultService;

  public ObjectiveServiceImpl(ObjectiveRepository repository, KeyResultService keyResultService) {
    super(repository);
    this.repository = repository;
    this.keyResultService = keyResultService;
  }

  @Override
  @Transactional
  public ObjectiveResponse create(
      String title,
      String description,
      Integer startDate,
      Integer endDate,
      OkrsType type,
      OkrsTimeType timePeriodType,
      String userId) {
    log.info("(create)title : {}, type : {}, timePeriodType : {}, userId : {}", title, type, timePeriodType, userId);

    return ObjectiveResponse.from(
        create(
            Objective.of(
                title,
                description,
                DateUtils.getEpochTime(startDate),
                DateUtils.getEpochTime(endDate),
                type,
                timePeriodType,
                userId)));
  }

  @Override
  @Transactional
  public void deleteById(String id) {
    log.info("(deleteById)id: {}", id);
    if (!isExist(id)) {
      log.error("(deleteById)id: {} not found", id);
      throw new NotFoundException(id, Objective.class.getSimpleName());
    }
    keyResultService.deleteAllByObjectiveId(id);
    delete(id);
  }

  @Override
  @Transactional(readOnly = true)
  public ObjectiveDetailResponse getById(String id) {
    log.debug("(getById)id : {}", id);
    if (!isExist(id)) {
      log.error("(getById)id: {} not found", id);
      throw new NotFoundException(id, Objective.class.getSimpleName());
    }
    var objective = find(id);
    return ObjectiveDetailResponse.from(objective, keyResultService.findByObjectiveId(id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ObjectiveResponse> list(String userId) {
    log.debug("(list)userId : {}", userId);
    return repository.findByUserId(userId).stream()
        .map(ObjectiveResponse::from)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ObjectiveResponse update(
      String id,
      String title,
      String description,
      Integer startDate,
      Integer endDate,
      OkrsType type,
      OkrsTimeType timePeriodType,
      String userId
  ) {
    log.info("(update)id : {}, title : {}, userId : {}", id, title, userId);

    if (!isExist(id)) {
      log.error("(update)id : {} --> NOT FOUND EXCEPTION", id);
      throw new NotFoundException(id, Objective.class.getSimpleName());
    }

    var objective = find(id);
    if (!objective.getUserId().equals(userId)) {
      log.error("(update)userId : {} --> FORBIDDEN EXCEPTION", userId);
      throw new ForbiddenException(userId);
    }
    objective.setTitle(title);
    objective.setDescription(description);
    objective.setStartDate(DateUtils.getEpochTime(startDate));
    objective.setEndDate(DateUtils.getEpochTime(endDate));
    objective.setType(type);
    objective.setTimePeriodType(timePeriodType);
    return ObjectiveResponse.from(update(objective));
  }

  @Override
  @Transactional(readOnly = true)
  public void validateKeyResultPeriodTime(
      String id, Integer keyResultStartDate, Integer keyResultEndDate) {
    log.info("(validateKeyResultPeriodTime)id : {}, keyResultStartDate : {}, keyResultEndDate : {}",
        id, keyResultStartDate, keyResultEndDate);

    if (!isExist(id)) {
      log.error("(validateKeyResultPeriodTime)objectiveId : {} --> NOT FOUND EXCEPTION", id);
      throw new NotFoundException(id, Objective.class.getSimpleName());
    }

    var objective = find(id);
    var objectiveStartDate = DateUtils.getDate(objective.getStartDate());
    var objectiveEndDate = DateUtils.getDate(objective.getEndDate());

    if (keyResultStartDate < objectiveStartDate
        || keyResultStartDate > objectiveEndDate
        || keyResultEndDate < objectiveStartDate
        || keyResultEndDate > objectiveEndDate) {
      log.error("(validateKeyResultPeriodTime)keyResultStartDate : {}, keyResultStartDate : {}, id : {} --> OkrsKeyResultPeriodTimeException",
          keyResultStartDate, keyResultEndDate, id);
      throw new OkrsKeyResultPeriodTimeException(
          String.valueOf(keyResultStartDate), String.valueOf(keyResultEndDate), id);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public void validateExist(String objectiveId) {
    log.info("(validateExist)objectiveId : {}", objectiveId);
    if (!isExist(objectiveId)) {
      log.error("(validateExist)objectiveId : {} --> NOT FOUND EXCEPTION", objectiveId);
      throw new NotFoundException(objectiveId, Objective.class.getSimpleName());
    }
  }
}
