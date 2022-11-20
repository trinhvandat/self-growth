package org.ptit.okrs.core_authentication.service;

import java.util.Map;
import org.ptit.okrs.core_redis.BaseRedisHashService;

public interface LoginFailService extends BaseRedisHashService<Long> {

  /**
   * increase after login fail
   * @param accountId - account id of user account
   */
  void increaseFailAttempts(String accountId);

  /**
   * check account lock or not
   * @param accountId - account id of user account
   * @return boolean
   */
  Boolean isTemporaryLock(String accountId);

  /**
   * reset fail attempt and unlock after login success
   * @param accountId - account id of user account
   */
  void resetFailAttempts(String accountId);

  /**
   * return param map for i18n message
   * @param accountId  - id of account user
   * @return param map
   */
  Map<String, String> returnParamMaps(String accountId);

  /**
   * lock account when login fail reach to limit
   * @param accountId - account id of user account
   */
  void setLock(String accountId);
}
