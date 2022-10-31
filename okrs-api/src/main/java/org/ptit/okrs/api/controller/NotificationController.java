package org.ptit.okrs.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.model.request.NotificationRequest;
import org.ptit.okrs.api.model.response.OkrsResponse;
import org.ptit.okrs.core.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.ptit.okrs.api.constant.OkrsApiConstant.BaseUrl.NOTIFICATION_BASE_URL;

@RestController
@RequestMapping(NOTIFICATION_BASE_URL)
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService service;

    @ApiOperation("Create new notification")
    @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response")
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OkrsResponse create(@Validated @RequestBody NotificationRequest request) {
        log.info("(create)request: {}", request);
        return OkrsResponse.of(
                HttpStatus.CREATED.value(),
                service.create(
                        request.getContent(),
                        "Default user id" //TODO: id of the user -> get by auth
                )
        );
    }

    @ApiOperation("Get list notification")
    @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public OkrsResponse list() {
        return OkrsResponse.of(HttpStatus.OK.value(), service.list());
    }

    @ApiOperation("Get notification by id")
    @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{id}")
    public OkrsResponse get(@PathVariable("id") String id) {
        log.info("(getById)id : {}", id);
        return OkrsResponse.of(HttpStatus.OK.value(), service.getById(id));
    }

    @ApiOperation("Update notification")
    @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{id}")
    public OkrsResponse update(@PathVariable("id") String id, @Validated @RequestBody NotificationRequest request) {
        log.info("(update)id : {}, request : {}", id, request);
        return OkrsResponse.of(HttpStatus.OK.value(), service.update(
                request.getId(),
                request.getContent(),
                "userId"));
    }

    @ApiOperation("Delete notification")
    @ApiResponse(code = 200, message = "Successfully response.")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") String id) {
        log.info("(deleteById)id : {}", id);
        service.deleteById(id);
    }

}
