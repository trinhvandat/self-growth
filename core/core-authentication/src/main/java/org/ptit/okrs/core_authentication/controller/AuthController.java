package org.ptit.okrs.core_authentication.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.dto.request.AuthUserRegisterRequest;
import org.ptit.okrs.core_authentication.dto.response.AuthUserRegisterResponse;
import org.ptit.okrs.core_authentication.facade.AuthFacadeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/users")
@RestController
@Slf4j
public class AuthController {

  private final AuthFacadeService authFacadeService;

  public AuthController(AuthFacadeService authFacadeService) {
    this.authFacadeService = authFacadeService;
  }

  @ApiOperation("Step 1: Create new user for sign up account")
  @ApiResponse(code = 201, message = "Successfully response.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AuthUserRegisterResponse createUser(@Valid @RequestBody AuthUserRegisterRequest request) {
    log.info("(createUser)request: {}", request);
    return authFacadeService.register(request);
  }
}
