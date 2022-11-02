package org.ptit.okrs.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.model.request.NotificationRequest;
import org.ptit.okrs.api.model.response.OkrsResponse;
import org.ptit.okrs.core.model.NotificationResponse;
import org.ptit.okrs.core.paging.PagingReq;
import org.ptit.okrs.core.paging.PagingRes;
import org.ptit.okrs.core.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                        request.getUserId()
                )
        );
    }

    @ApiOperation("Get list notification")
    @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = {"user_id"})
    public ResponseEntity<PagingRes<NotificationResponse>> list(@RequestParam("user_id") String userId, @Validated() final PagingReq pagingReq){
        final Page<NotificationResponse> notificationResponses = service.list(userId, pagingReq.makePageable());
        return new ResponseEntity<>(PagingRes.of(notificationResponses) , HttpStatus.OK);
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
                request.getUserId()));
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
