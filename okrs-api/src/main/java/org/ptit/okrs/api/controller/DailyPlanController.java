package org.ptit.okrs.api.controller;

import static org.ptit.okrs.api.constant.OkrsApiConstant.BaseUrl.DAILY_PLAN_BASE_URL;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.model.request.DailyPlanCreateRequest;
import org.ptit.okrs.api.model.request.DailyPlanUpdateRequest;
import org.ptit.okrs.api.model.response.OkrsResponse;
import org.ptit.okrs.core.constant.DailyPlanStatus;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.KeyResultService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(DAILY_PLAN_BASE_URL)
@RequiredArgsConstructor
@RestController
@Slf4j
public class DailyPlanController {

  private final DailyPlanService service;
  private final KeyResultService keyResultService;

  @ApiOperation("Create new task in daily plan")
  @ApiResponse(code = 201, response = OkrsResponse.class, message = "Successfully response")
  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public OkrsResponse create(@Validated @RequestBody DailyPlanCreateRequest request) {
    log.info("(create)request: {}", request);
    if(Objects.nonNull(request.getKeyResultId())) {
      keyResultService.validateExist(request.getKeyResultId());
    }
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
  public OkrsResponse delete(@PathVariable("id") String id) {
    log.info("(delete)id: {}", id);
    service.delete(id);
    return OkrsResponse.of(HttpStatus.OK.value());
  }

  @ApiOperation("Get list task of daily plan by key result id")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/{id}/key-result-id")
  public OkrsResponse getByKeyResultId(@PathVariable("id") String id) {
    keyResultService.validateExist(id);
    if (log.isDebugEnabled()) {
      log.debug("(get)id: {}", id);
    }
    return OkrsResponse.of(HttpStatus.OK.value(), service.getByKeyResultId(id));
  }

  @ApiOperation("Get list task of daily plan by date")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(params = "date")
  public OkrsResponse getByDate(
      @ApiParam(required = true) @RequestParam("date") Integer date
  ) {
    if (log.isDebugEnabled()) {
      log.debug("(list)date: {}", date);
    }
    return OkrsResponse.of(HttpStatus.OK.value(), service.getByDate(date));
  }

  @ApiOperation("Link task of daily plan to key result")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @PutMapping(path = "/{id}/link-key-result")
  public OkrsResponse linkDailyPlanToKeyResults(
      @PathVariable("id") String id,
      @ApiParam(required = true) @RequestParam("link-key-result") String keyResultId
      //@Validated @RequestBody String keyResultId
  ) {
    log.info("(linkDailyPlanToKeyResults)id: {}", id);
    keyResultService.validateExist(keyResultId);
    return OkrsResponse.of(HttpStatus.OK.value(),
        service.linkDailyPlanToKeyResults(id, keyResultId));
  }

  @ApiOperation("Update task of daily plan")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @PutMapping(path = "{id}")
  public OkrsResponse update(@PathVariable("id") String id, @Validated @RequestBody
  DailyPlanUpdateRequest request) {
    log.info("(update)id: {}, title: {}", id, request.getTitle());
    if(Objects.nonNull(request.getKeyResultId())) {
      keyResultService.validateExist(request.getKeyResultId());
    }
    return OkrsResponse.of(HttpStatus.OK.value(),
        service.update(id,
            request.getTitle(),
            request.getDescription(),
            request.getDate(),
            request.getNote(),
            request.getKeyResultId()));
  }

  @ApiOperation("Update status task of daily plan")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @PatchMapping(path = "/{id}/status")
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse updateStatusDailyPlan(@PathVariable("id") String id,
      @ApiParam(required = true) @RequestParam("status") DailyPlanStatus status) {
    log.info("(updateStatusDailyPlan)id: {}", id);
    return OkrsResponse.of(HttpStatus.OK.value(), service.updateStatusDailyPlan(id, status));
  }
}
