package org.ptit.okrs.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.model.DailyPlanCreateRequest;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.service.DailyPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.ptit.okrs.api.constant.OkrsApiConstant.BaseUrl.DAILY_PLAN_BASE_URL;

@RequestMapping(DAILY_PLAN_BASE_URL)
@RequiredArgsConstructor
@RestController
@Slf4j
public class DailyPlanController {
  private final DailyPlanService service;

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public DailyPlanResponse create(@Validated @RequestBody DailyPlanCreateRequest request) {
    log.info("(create)request: {}", request);
    return service.create(
        request.getTitle(),
        request.getDescription(),
        "Default user id", //TODO: id of the user -> get by auth
        request.getKeyResultId()
    );
  }
}
