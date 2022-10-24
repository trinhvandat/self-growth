package org.ptit.okrs.core.constant;

public enum OkrsType {
  COMMIT,
  EXPAND;

  public static Boolean contains(String value) {
    for (OkrsTimeType type : OkrsTimeType.values()) {
      if(type.name().equals(value)) {
        return true;
      }
    }
    return false;
  }
}
