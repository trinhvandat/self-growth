package org.ptit.okrs.api.controller;

import static org.ptit.okrs.api.constant.OkrsApiConstant.BaseUrl.USER_BASE_URL;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.model.request.UserCreateRequest;
import org.ptit.okrs.api.model.request.UserUpdateAvatarRequest;
import org.ptit.okrs.api.model.request.UserUpdateRequest;
import org.ptit.okrs.api.model.response.OkrsResponse;
import org.ptit.okrs.core.service.AccountService;
import org.ptit.okrs.core.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(USER_BASE_URL)
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {
  private final UserService service;
  private final AccountService accountService;

  @ApiOperation("Create a new user")
  @ApiResponse(code = 201, response = OkrsResponse.class, message = "Successfully response.")
  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public OkrsResponse create(@RequestBody @Validated UserCreateRequest userCreateRequest) {
    log.info("(create)userCreateRequest: {}", userCreateRequest);
    return OkrsResponse.of(
        HttpStatus.CREATED.value(),
        service.create(
            userCreateRequest.getName(),
            userCreateRequest.getEmail()));
  }

  @ApiOperation("Update a user")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @PutMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse update(@RequestBody @Validated UserUpdateRequest userUpdateRequest) {
    log.info("(update)userUpdateRequest: {}", userUpdateRequest);
    return OkrsResponse.of(
        HttpStatus.OK.value(),
        service.update(
            "userId", // TODO: get user id by auth
            userUpdateRequest.getName(),
            userUpdateRequest.getPhone(),
            userUpdateRequest.getDateOfBirth(),
            userUpdateRequest.getGender(),
            userUpdateRequest.getAddress()));
  }

  @ApiOperation("Get details a user")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public OkrsResponse get() {
    log.info("(get)");
    return OkrsResponse.of(
        HttpStatus.OK.value(), service.get("userId")); // TODO: get userId by auth
  }

  @ApiOperation("Update a user's avatar")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @PatchMapping
  @ResponseStatus(HttpStatus.OK)
  public OkrsResponse updateAvatar(
      @RequestBody @Validated UserUpdateAvatarRequest userUpdateAvatarRequest) {
    log.info("(updateAvatar)userUpdateAvatarRequest: {}", userUpdateAvatarRequest);
    return OkrsResponse.of(
        HttpStatus.OK.value(),
        service.changePathAvatar("userId", userUpdateAvatarRequest.getPathAvatar())); // TODO: userId get by auth
  }

  @ApiOperation("Get a user's avatar")
  @ApiResponse(code = 200, response = OkrsResponse.class, message = "Successfully response.")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/avatar")
  public OkrsResponse getAvatar() {
    log.info("(getAvatar)");
    return OkrsResponse.of(
        HttpStatus.OK.value(), service.getPathAvatar("userId")); // TODO: userId get by auth
  }
}
