package org.ptit.okrs.core.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.entity.KeyResult;
import org.ptit.okrs.core.exception.ExistedKeyResultTitleException;
import org.ptit.okrs.core.model.KeyResultResponse;
import org.ptit.okrs.core.repository.KeyResultRepository;
import org.ptit.okrs.core.service.KeyResultService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.ForbiddenException;
import org.ptit.okrs.core_exception.NotFoundException;
import org.ptit.okrs.core_util.DateUtils;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class KeyResultServiceImpl extends BaseServiceImpl<KeyResult> implements KeyResultService {

  private final KeyResultRepository repository;

  public KeyResultServiceImpl(KeyResultRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  @Transactional
  public KeyResultResponse create(
      String objectiveId,
      String title,
      String description,
      Integer startDate,
      Integer endDate,
      Integer progress,
      String userId) {
    log.info(
        "(create)objectiveId : {}, title : {}, description : {}, startDate : {}, endDate : {}, progress : {}, userId : {}",
        objectiveId,
        title,
        description,
        startDate,
        endDate,
        progress,
        userId);
    if (repository.existsByTitleAndObjectiveId(title, objectiveId)) {
      log.error(
          "(create)title : {}, objectiveId : {} --> ExistedKeyResultTitleException",
          title,
          objectiveId);
      throw new ExistedKeyResultTitleException(title, objectiveId);
    }
    return KeyResultResponse.from(
        create(
            KeyResult.of(
                objectiveId,
                title,
                description,
                DateUtils.getEpochTime(startDate),
                DateUtils.getEpochTime(endDate),
                progress,
                userId)));
  }

  @Override
  @Transactional
  public void deleteAllByObjectiveId(String objectiveId) {
    log.info("(deleteAllByObjectiveId)objectiveId : {}", objectiveId);
    repository.deleteAllByObjectiveId(objectiveId);
  }

  @Override
  @Transactional
  public void deleteById(String id, String objectiveId) {
    if (!isExist(id)) {
      log.error("(deleteById)id : {} --> NOT FOUND EXCEPTION", id);
      throw new NotFoundException(id, KeyResult.class.getSimpleName());
    }
    delete(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<KeyResultResponse> findByObjectiveId(String objectiveId) {
    log.info("(findByObjectiveId)objectiveId : {}", objectiveId);
    return repository.find(objectiveId).stream()
        .map(KeyResultResponse::from)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public KeyResultResponse update(
      String id,
      String objectiveId,
      String title,
      String description,
      Integer startDate,
      Integer endDate,
      Integer progress,
      String userId) {
    log.info(
        "(update)id : {}, objectiveId : {}, title : {}, description : {}, startDate : {}, endDate : {}, progress : {}, userId : {}",
        id,
        objectiveId,
        title,
        description,
        startDate,
        endDate,
        progress,
        userId);
    var keyResult = find(id);
    if (keyResult == null) {
      log.error("(update)id : {} --> NOT FOUND EXCEPTION", id);
      throw new NotFoundException(id, KeyResult.class.getSimpleName());
    }
    if (!keyResult.getUserId().equals(userId)) {
      log.error("(update)userId : {} --> FORBIDDEN EXCEPTION", userId);
      throw new ForbiddenException(userId);
    }
    keyResult.setObjectiveId(objectiveId);
    keyResult.setTitle(title);
    keyResult.setDescription(description);
    keyResult.setStartDate(DateUtils.getEpochTime(startDate));
    keyResult.setEndDate(DateUtils.getEpochTime(endDate));
    keyResult.setProgress(progress);
    return KeyResultResponse.from(update(keyResult));
  }

  @Override
  @Transactional
  public void updateProgress(String id, String userId, Integer progress) {
    log.info("(updateProgress)id : {}, userId : {}, progress : {}", id, userId, progress);
    var keyResult = find(id);
    if (keyResult == null) {
      log.error("(updateProgress)id : {} --> NOT FOUND EXCEPTION", id);
      throw new NotFoundException(id, KeyResult.class.getSimpleName());
    }
    if (!keyResult.getUserId().equals(userId)) {
      log.error("(updateProgress)userId : {} --> FORBIDDEN EXCEPTION", userId);
      throw new ForbiddenException(userId);
    }
    keyResult.setProgress(progress);
    update(keyResult);
  }

  @Override
  @Transactional(readOnly = true)
  public void validateExist(String keyResultId) {
    if (!isExist(keyResultId)) {
      throw new NotFoundException(keyResultId, KeyResult.class.getSimpleName());
    }
  }
}
