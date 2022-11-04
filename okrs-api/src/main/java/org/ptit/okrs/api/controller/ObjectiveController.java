package org.ptit.okrs.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.constant.OkrsApiConstant.BaseUrl;
import org.ptit.okrs.api.model.request.ObjectiveCreateRequest;
import org.ptit.okrs.api.model.request.ObjectiveUpdateRequest;
import org.ptit.okrs.api.model.response.OkrsResponse;
import org.ptit.okrs.core.service.ObjectiveService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseUrl.OBJECTIVE_BASE_URL)
@RequiredArgsConstructor
@Slf4j
public class ObjectiveController {

  private final ObjectiveService service;

  @ApiOperation("Create new objective")
  @ApiResponse(code = 201, response = OkrsResponse.class, message = "Successfully response.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OkrsResponse create(@Validated @RequestBody ObjectiveCreateRequest request) {
    log.info("(create)request : {}", request);
    request.validate();
    return OkrsResponse.of(
        HttpStatus.CREATED.value(),
        service.create(
            request.getTitle(),
            request.getDescription(),
            request.getStartDate(),
            request.getEndDate(),
            request.getType(),
            request.getTimePeriodType(),
            "e2e46eca-8e51-405a-b813-771dbbb5ef6e"));
  }

  @ApiOperation("Delete an objective")
  @ApiResponse(code = 200, message = "Successfully response.")
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse deleteById(@PathVariable("id") String id) {
    log.info("(deleteById)id : {}", id);
    service.deleteById(id);
    return OkrsResponse.of(HttpStatus.OK.value());
  }

  @ApiOperation("Get a detail objective")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse getById(@PathVariable("id") String id) {
    log.info("(getById)id : {}", id);
    return OkrsResponse.of(HttpStatus.OK.value(), service.getById(id));
  }

  @ApiOperation("Get list of all objectives")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse list() {
    log.info("(list)");
    return OkrsResponse.of(
        HttpStatus.OK.value(), service.list("e2e46eca-8e51-405a-b813-771dbbb5ef6e"));
  }

  @ApiOperation("Update an objective")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse update(
      @PathVariable("id") String id, @Validated @RequestBody ObjectiveUpdateRequest request) {
    log.info("(update)id : {}, request : {}", id, request);
    request.validate();
    request.validatePathVariable(id);
    return OkrsResponse.of(
        HttpStatus.OK.value(),
        service.update(
            request.getId(),
            request.getTitle(),
            request.getDescription(),
            request.getStartDate(),
            request.getEndDate(),
            request.getType(),
            request.getTimePeriodType(),
            "e2e46eca-8e51-405a-b813-771dbbb5ef6e"));
  }
}
