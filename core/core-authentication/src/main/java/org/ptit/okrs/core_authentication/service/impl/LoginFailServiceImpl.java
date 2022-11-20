package org.ptit.okrs.core_authentication.service.impl;

import static org.ptit.okrs.core_authentication.constant.CacheConstant.LoginFail.KEY_CACHE_FAIL_ATTEMPTS;
import static org.ptit.okrs.core_authentication.constant.CacheConstant.LoginFail.KEY_CACHE_UNLOCK_TIME;
import static org.ptit.okrs.core_authentication.constant.LoginFailConstant.FIRST_LOCK_LIMIT;
import static org.ptit.okrs.core_authentication.constant.LoginFailConstant.FIRST_LOCK_TIME;
import static org.ptit.okrs.core_authentication.constant.LoginFailConstant.INIT_FAIL_ATTEMPTS;
import static org.ptit.okrs.core_authentication.constant.LoginFailConstant.SECOND_LOCK_LIMIT;
import static org.ptit.okrs.core_authentication.constant.LoginFailConstant.SECOND_LOCK_TIME;
import static org.ptit.okrs.core_authentication.constant.LoginFailConstant.THIRD_LOCK_LIMIT;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.service.AuthAccountService;
import org.ptit.okrs.core_authentication.service.LoginFailService;
import org.ptit.okrs.core_redis.BaseRedisHashServiceImpl;
import org.ptit.okrs.core_util.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class LoginFailServiceImpl extends BaseRedisHashServiceImpl<Long>
    implements LoginFailService {

  private final AuthAccountService authAccountService;

  public LoginFailServiceImpl(
      RedisTemplate<String, Object> redisTemplate, AuthAccountService authAccountService) {
    super(redisTemplate);
    this.authAccountService = authAccountService;
  }
  @Override
  public void increaseFailAttempts(String accountId) {
    log.info("(increaseFailAttempts)accountId : {}", accountId);
    Long failAttempts = (Long) get(KEY_CACHE_FAIL_ATTEMPTS, accountId);
    if (failAttempts == null) {
      failAttempts = INIT_FAIL_ATTEMPTS;
    }
    failAttempts++;
    set(KEY_CACHE_FAIL_ATTEMPTS, accountId, failAttempts);
  }

  @Override
  public Boolean isTemporaryLock(String accountId) {
    log.info("(isAccountLock)accountId : {}", accountId);
    Long unlockTime = (Long) get(KEY_CACHE_UNLOCK_TIME, accountId);
    if (unlockTime == null) {
      return false;
    }
    return DateUtils.getCurrentEpoch() < unlockTime;
  }

  @Override
  public void resetFailAttempts(String accountId) {
    log.info("(resetFailAttempts)accountId : {}", accountId);
    delete(KEY_CACHE_FAIL_ATTEMPTS, accountId);
    delete(KEY_CACHE_UNLOCK_TIME, accountId);
  }

  @Override
  public Map<String, String> returnParamMaps(String accountId) {
    log.info("(returnParamMaps)accountId : {}", accountId);
    Map<String, String> params = new HashMap<>();
    Long failAttempts = (Long) get(KEY_CACHE_FAIL_ATTEMPTS, accountId);
    log.info("(returnParamMaps)failAttempts : {}", failAttempts);
    if (failAttempts == null) {
      return null;
    }
    if (failAttempts >= THIRD_LOCK_LIMIT) {
      params.put("failAttempts", String.valueOf(THIRD_LOCK_LIMIT));
      return params;
    }
    if (failAttempts >= SECOND_LOCK_LIMIT) {
      params.put("failAttempts", String.valueOf(SECOND_LOCK_LIMIT));
      params.put("lockTime", String.valueOf(SECOND_LOCK_TIME));
      return params;
    }
    params.put("failAttempts", String.valueOf(FIRST_LOCK_LIMIT));
    params.put("lockTime", String.valueOf(FIRST_LOCK_TIME));
    return params;
  }

  @Override
  @Transactional
  public void setLock(String accountId) {
    log.info("(setLockAccount)accountId : {}", accountId);
    Long failAttempts = (Long) get(KEY_CACHE_FAIL_ATTEMPTS, accountId);
    log.info("(setLockAccount)failAttempts : {}", failAttempts);
    if (failAttempts.equals(THIRD_LOCK_LIMIT)) {
      authAccountService.enableLockPermanent(accountId);
    }
    if (failAttempts.equals(SECOND_LOCK_LIMIT)) {
      set(KEY_CACHE_UNLOCK_TIME, accountId, DateUtils.getCurrentEpoch() + SECOND_LOCK_TIME);
    }
    if (failAttempts.equals(FIRST_LOCK_LIMIT)) {
      set(KEY_CACHE_UNLOCK_TIME, accountId, DateUtils.getCurrentEpoch() + FIRST_LOCK_TIME);
    }
  }
}
