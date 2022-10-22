package org.ptit.okrs.core.constant;

public enum OkrsTimeType {
  A_MONTH,
  THREE_MONTH,
  SIX_MONTH,
  A_YEAR,
  THREE_YEAR,
  CUSTOM;

  public static Boolean contains(String value) {
    for (OkrsTimeType type : OkrsTimeType.values()) {
      if(type.name().equals(value)) {
        return true;
      }
    }
    return false;
  }
}
