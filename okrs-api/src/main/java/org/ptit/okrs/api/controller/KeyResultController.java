package org.ptit.okrs.api.controller;

import static org.ptit.okrs.api.constant.OkrsApiConstant.ResourceConstant.KEY_RESULT;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.constant.OkrsApiConstant.BaseUrl;
import org.ptit.okrs.api.model.request.KeyResultCreateRequest;
import org.ptit.okrs.api.model.request.KeyResultUpdateProgressRequest;
import org.ptit.okrs.api.model.request.KeyResultUpdateRequest;
import org.ptit.okrs.api.model.response.OkrsResponse;
import org.ptit.okrs.core.service.KeyResultService;
import org.ptit.okrs.core.service.ObjectiveService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseUrl.OBJECTIVE_BASE_URL)
@Slf4j
public class KeyResultController {

  private final KeyResultService service;
  private final ObjectiveService objectiveService;

  @ApiOperation("Create new key result")
  @ApiResponse(code = 201, response = OkrsResponse.class, message = "Successfully response.")
  @PostMapping(value = "/{objective_id}/" + KEY_RESULT)
  @ResponseStatus(HttpStatus.CREATED)
  public OkrsResponse create(
      @PathVariable("objective_id") String objectiveId,
      @Validated @RequestBody KeyResultCreateRequest request) {
    log.info("(create)objectiveId : {}, request : {}", objectiveId, request);
    request.validate();
    objectiveService.validateExist(request.getObjectiveId());
    objectiveService.validateKeyResultPeriodTime(
        request.getObjectiveId(), request.getStartDate(), request.getEndDate());
    return OkrsResponse.of(
        HttpStatus.CREATED.value(),
        service.create(
            request.getObjectiveId(),
            request.getTitle(),
            request.getDescription(),
            request.getStartDate(),
            request.getEndDate(),
            request.getProgress(),
            "userId"));
  }

  @ApiOperation("Delete a key result")
  @ApiResponse(code = 200, message = "Successfully response.")
  @DeleteMapping(value = "/{objective_id}/" + KEY_RESULT + "/{key_result_id}")
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse deleteById(
      @PathVariable("objective_id") String objectiveId,
      @PathVariable("key_result_id") String keyResultId) {
    log.info("(deleteById)objectiveId : {}, keyResultId : {}", objectiveId, keyResultId);
    objectiveService.validateExist(objectiveId);
    service.deleteById(keyResultId, objectiveId);
    return OkrsResponse.of(HttpStatus.OK.value());
  }

  @ApiOperation("Update a key result")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @PutMapping(value = "/{objective_id}/" + KEY_RESULT + "/{key_result_id}")
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse update(
      @PathVariable("objective_id") String objectiveId,
      @PathVariable("key_result_id") String keyResultId,
      @Validated @RequestBody KeyResultUpdateRequest request) {
    log.info(
        "(update)objectiveId : {}, keyResultId : {}, request : {}",
        objectiveId,
        keyResultId,
        request);
    return OkrsResponse.of(
        HttpStatus.OK.value(),
        service.update(
            request.getId(),
            request.getObjectiveId(),
            request.getTitle(),
            request.getDescription(),
            request.getStartDate(),
            request.getEndDate(),
            request.getProgress(),
            "userId"));
  }

  @ApiOperation("Update the progress of a key result")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @PatchMapping(value = "/{objective_id}/" + KEY_RESULT + "/{key_result_id}")
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse updateProgress(
      @PathVariable("objective_id") String objectiveId,
      @PathVariable("key_result_id") String keyResultId,
      @Validated @RequestBody KeyResultUpdateProgressRequest request) {
    log.info(
        "(updateProgress)objectiveId : {}, keyResultId : {}, request : {}",
        objectiveId,
        keyResultId,
        request);
    return OkrsResponse.of(
        HttpStatus.OK.value(),
        service.updateProgress(request.getId(), request.getObjectiveId(), request.getProgress()));
  }
}
