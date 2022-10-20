package org.ptit.okrs.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.model.request.DailyPlanCreateRequest;
import org.ptit.okrs.api.model.response.OkrsResponse;
import org.ptit.okrs.core.model.DailyPlanResponse;
import org.ptit.okrs.core.model.GetDetailDailyPlanResponse;
import org.ptit.okrs.core.service.DailyPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.ptit.okrs.api.constant.OkrsApiConstant.BaseUrl.DAILY_PLAN_BASE_URL;

@RequestMapping(DAILY_PLAN_BASE_URL)
@RequiredArgsConstructor
@RestController
@Slf4j
public class DailyPlanController {
  private final DailyPlanService service;

  @ApiOperation("Create new task in daily plan")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response")
  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public OkrsResponse create(@Validated @RequestBody DailyPlanCreateRequest request) {
    log.info("(create)request: {}", request);
    return OkrsResponse.of(
        HttpStatus.CREATED.value(),
        service.create(
            request.getTitle(),
            request.getDescription(),
            "Default user id", //TODO: id of the user -> get by auth
            request.getKeyResultId()
        )
    );
  }

  @ApiOperation("Get list task of daily plan by date")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(params = "date")
  public OkrsResponse list(
      @ApiParam(required = true) @RequestParam("date") Integer date
  ) {
    if (log.isDebugEnabled()) log.debug("(list)date: {}", date);
    return OkrsResponse.of(HttpStatus.OK.value(), new ArrayList<DailyPlanResponse>());
  }

  @ApiOperation("Get detail task of daily plan by date")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "{id}")
  public OkrsResponse get(@PathVariable("id") String id) {
    if (log.isDebugEnabled()) log.debug("(get)id: {}", id);
    return OkrsResponse.of(HttpStatus.OK.value(), new GetDetailDailyPlanResponse());
  }

  @ApiOperation("Update task of daily plan")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @PutMapping(path = "{id}")
  public OkrsResponse update(@PathVariable("id") String id) {
    log.info("(update)id: {}", id);
    return OkrsResponse.of(HttpStatus.OK.value(), new DailyPlanResponse());
  }
}
