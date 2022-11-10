package org.ptit.okrs.core_util;

import java.text.DecimalFormat;
import java.util.Random;

public class GeneratorUtils {
  private static final String FOUR_DIGITS_STRING = "000000";
  private static final Integer FOUR_DIGITS_UPPER_BOUND = 10000;

  public static String generateOtp() {
    Random random = new Random();
    return new DecimalFormat(FOUR_DIGITS_STRING).format(random.nextInt(FOUR_DIGITS_UPPER_BOUND));
  }
}
