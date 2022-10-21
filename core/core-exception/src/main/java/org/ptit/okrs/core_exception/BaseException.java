package org.ptit.okrs.core_exception;

import lombok.Data;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class BaseException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 128763123L;

  private int status = 0;
  private String code = "";
  private String message = "";
  private Map<String, String> params;

  public void addParams(String key, String value) {
    if (Objects.isNull(params)) {
      params = new HashMap<>();
    }
    params.put(key, value);
  }
}
