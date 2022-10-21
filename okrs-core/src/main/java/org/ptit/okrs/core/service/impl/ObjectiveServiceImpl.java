package org.ptit.okrs.core.service.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.entity.Objective;
import org.ptit.okrs.core.model.ObjectiveDetailResponse;
import org.ptit.okrs.core.model.ObjectiveResponse;
import org.ptit.okrs.core.repository.ObjectiveRepository;
import org.ptit.okrs.core.service.ObjectiveService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;

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
    return null;
  }

  @Override
  public void deleteById(String id) {}

  @Override
  public ObjectiveDetailResponse getById(String id) {
    return null;
  }

  @Override
  public List<ObjectiveResponse> list(String userId) {
    return null;
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
}
