package org.ptit.okrs.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.model.request.DailyPlanCreateRequest;
import org.ptit.okrs.api.model.request.DailyPlanUpdateRequest;
import org.ptit.okrs.api.model.response.OkrsResponse;
import org.ptit.okrs.core.constant.DailyPlanStatus;
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

  @ApiOperation("Delete task of daily plan by id")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(path = "{id}")
  public void delete(@PathVariable("id") String id) {
    log.info("(delete)id: {}", id);
    service.deleteById(id);
  }

  @ApiOperation("Link task of daily plan to key result")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @PatchMapping(path = "/link/{id}")
  public OkrsResponse linkDailyPlanToKeyResults(
      @PathVariable("id") String id,
      @ApiParam(required = true) @RequestParam("keyResultId") String keyResultId
  ) {
    log.info("(linkDailyPlanToKeyResults)id: {}", id);
    return OkrsResponse.of(HttpStatus.OK.value(), service.linkDailyPlanToKeyResults(id, keyResultId));
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
  public OkrsResponse update(@PathVariable("id") String id, @Validated @RequestBody
  DailyPlanUpdateRequest request) {
    log.info("(update)id: {}, title: {}", id, request.getTitle());
    return OkrsResponse.of(HttpStatus.OK.value(),
        service.update(id,
            request.getTitle(),
            request.getDescription(),
            request.getDate(),
            request.getNote(),
            "Default user id", //TODO: id of the user -> get by auth
            request.getKeyResultId()));
  }

  @ApiOperation("Update status task of daily plan")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @PatchMapping(path = "/{id}/status")
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse updateStatusDailyPlan(@PathVariable("id") String id, @Validated @RequestBody
  DailyPlanStatus status) {
    log.info("(updateStatusDailyPlan)id: {}", id);
    return OkrsResponse.of(HttpStatus.OK.value(), service.updateStatusDailyPlan(id, status));
  }
}
