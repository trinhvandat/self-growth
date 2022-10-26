package org.ptit.okrs.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.entity.KeyResult;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core.model.KeyResultResponse;
import org.ptit.okrs.core.repository.KeyResultRepository;
import org.ptit.okrs.core.service.KeyResultService;
import org.ptit.okrs.core.service.ObjectiveService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.NotFoundException;
import org.ptit.okrs.core_util.DateUtils;

@Slf4j
public class KeyResultServiceImpl extends BaseServiceImpl<KeyResult> implements KeyResultService {

  private final KeyResultRepository repository;

  public KeyResultServiceImpl(KeyResultRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
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
  public void deleteById(String id, String objectiveId) {}

  @Override
  public KeyResultResponse update(
      String id,
      String objectiveId,
      String title,
      String description,
      Integer startDate,
      Integer endDate,
      Integer progress,
      String userId) {
    return null;
  }

  @Override
  public KeyResultResponse updateProgress(String id, String objectiveId, Integer progress) {
    return null;
  }

  @Override
  public void validateExist(String keyResultId) {
    if (!isExist(keyResultId)) {
      throw new NotFoundException(keyResultId, KeyResult.class.getSimpleName());
    }
  }
}
