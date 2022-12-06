package org.ptit.okrs.core_authentication.service;

import org.ptit.okrs.core_redis.BaseRedisHashService;

public interface LoginFailService extends BaseRedisHashService<Long> {

  Long getFailAttempts(String email);

  Long getUnlockTime(String email);

  /**
   * increase after login fail
   *
   * @param email - email of user account
   */
  void increaseFailAttempts(String email);

  /**
   * check account lock or not
   *
   * @param email - email of user account
   * @return boolean
   */
  Boolean isTemporaryLock(String email);

  /**
   * reset fail attempt and unlock after login success
   *
   * @param email - email of user account
   */
  void resetFailAttempts(String email);

  /**
   * lock account when login fail reach to limit
   *
   * @param email - email of user account
   */
  void setLock(String email);
}
