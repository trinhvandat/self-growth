package org.ptit.okrs.core.service.impl;

import org.ptit.okrs.core.entity.KeyResult;
import org.ptit.okrs.core.model.KeyResultResponse;
import org.ptit.okrs.core.repository.KeyResultRepository;
import org.ptit.okrs.core.service.KeyResultService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;

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
    return null;
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
}
