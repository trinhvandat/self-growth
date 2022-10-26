package org.ptit.okrs.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.entity.KeyResult;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core.exception.OkrsDateInvalidException;
import org.ptit.okrs.core.model.ObjectiveDetailResponse;
import org.ptit.okrs.core.model.ObjectiveResponse;
import org.ptit.okrs.core.repository.ObjectiveRepository;
import org.ptit.okrs.core.service.ObjectiveService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.NotFoundException;
import org.ptit.okrs.core_util.DateUtils;

@Slf4j
public class ObjectiveServiceImpl extends BaseServiceImpl<Objective> implements ObjectiveService {

  private final ObjectiveRepository repository;

  public ObjectiveServiceImpl(ObjectiveRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public ObjectiveResponse create(
      String title,
      String description,
      Integer startDate,
      Integer endDate,
      OkrsType type,
      OkrsTimeType timePeriodType,
      String userId) {
    log.info(
        "(create)title : {}, description : {}, startDate : {}, endDate : {}, type : {}, timePeriodType : {}, userId : {}",
        title,
        description,
        startDate,
        endDate,
        type,
        timePeriodType,
        userId);
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
  public void deleteById(String id) {}

  @Override
  public ObjectiveDetailResponse getById(String id) {
    return null;
  }

  @Override
  public List<ObjectiveResponse> list(String userId) {
    log.info("(list)userId : {}", userId);
    return repository.find(userId).stream()
        .map(ObjectiveResponse::from)
        .collect(Collectors.toList());
  }

  @Override
  public ObjectiveResponse update(
      String id,
      String title,
      String description,
      Integer startDate,
      Integer endDate,
      OkrsType type,
      OkrsTimeType timePeriodType,
      String userId) {
    return null;
  }

  @Override
  public void validateKeyResultPeriodTime(
      String id, Integer keyResultStartDate, Integer keyResultEndDate) {
    log.info(
        "(validateKeyResultPeriodTime)id : {}, keyResultStartDate : {}, keyResultEndDate : {}",
        id,
        keyResultStartDate,
        keyResultEndDate);
    var objective = find(id);
    if (objective == null) {
      log.error("(validateKeyResultPeriodTime)objectiveId : {}", id);
      throw new NotFoundException(id, Objective.class.getSimpleName());
    }
    Integer objectiveStartDate = DateUtils.getDate(objective.getStartDate());
    Integer objectiveEndDate = DateUtils.getDate(objective.getEndDate());
    if (keyResultStartDate < objectiveStartDate || keyResultStartDate > objectiveEndDate) {
      log.error(
          "(validateKeyResultPeriodTime)keyResultStartDate : {}, keyResultStartDate : {}",
          keyResultStartDate,
          keyResultEndDate);
      throw new OkrsDateInvalidException(
          KeyResult.class.getSimpleName(),
          String.valueOf(keyResultStartDate),
          String.valueOf(keyResultEndDate));
    }
    if (keyResultEndDate < objectiveStartDate || keyResultEndDate > objectiveEndDate) {
      log.error(
          "(validateKeyResultPeriodTime)keyResultStartDate : {}, keyResultStartDate : {}",
          keyResultStartDate,
          keyResultEndDate);
      throw new OkrsDateInvalidException(
          KeyResult.class.getSimpleName(),
          String.valueOf(keyResultStartDate),
          String.valueOf(keyResultEndDate));
    }
  }

  @Override
  public void validateExist(String objectiveId) {
    log.info("(validateExist)objectiveId : {}", objectiveId);
    if(!isExist(objectiveId)) {
      log.error("(validateExist)objectiveId : {}", objectiveId);
      throw new NotFoundException(objectiveId, Objective.class.getSimpleName());
    }
  }
}
