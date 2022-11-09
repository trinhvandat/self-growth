package org.ptit.okrs.core_authentication.constant;

public class MailConstant {

  public static class MailForgotPassword {
    public static final String SUBJECT = "OKRS: Forgot Password!";
    public static final String TEMPLATE_NAME = "OTP-template";
    public static final String KEY_PARAM_OTP_TIME_LIFE = "time_life";
    public static final String KEY_PARAM_OTP = "otp";
  }
}
