package org.ptit.okrs.core_authentication.facade;

import org.ptit.okrs.core_authentication.dto.request.AuthUserRegisterRequest;
import org.ptit.okrs.core_authentication.dto.response.AuthUserRegisterResponse;

public interface AuthFacadeService {
  AuthUserRegisterResponse register(AuthUserRegisterRequest request);
}
