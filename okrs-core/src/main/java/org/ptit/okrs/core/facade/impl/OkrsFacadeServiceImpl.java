package org.ptit.okrs.core.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.facade.OkrsFacadeService;
import org.ptit.okrs.core.model.ObjectiveStatsResponse;
import org.ptit.okrs.core.service.KeyResultService;
import org.ptit.okrs.core.service.ObjectiveService;

@RequiredArgsConstructor
@Slf4j
public class OkrsFacadeServiceImpl implements OkrsFacadeService {

  private final ObjectiveService objectiveService;

  private final KeyResultService keyResultService;

  @Override
  public ObjectiveStatsResponse statistic(String objectiveId) {
    log.info("(statistic)objectiveId : {}", objectiveId);
    objectiveService.validateExist(objectiveId);
    return ObjectiveStatsResponse.of(
        objectiveId,
        keyResultService.findAvgProgressByObjectiveId(objectiveId),
        keyResultService.countAllByObjectiveId(objectiveId),
        keyResultService.countIncompleteByObjectiveId(objectiveId));
  }
}
