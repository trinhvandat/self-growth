package org.ptit.okrs.core_util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

  private ValidationUtils() {
  }

  /**
   * Function validate int date with date format: yyyyMMdd
   *
   * @param date - date need to check
   * @return boolean
   */
  public static boolean validateDate(Integer date) {
    var dateStr = String.valueOf(date).trim();
    try {
      LocalDate.parse(dateStr, DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      return false;
    } catch (RuntimeException e) {
      return false;
    }
    return true;
  }

  /**
   * Function validate start-date, end-date - If start-date, end date is invalid format -> return
   * false - If start-date greater than end-date -> return false
   *
   * @param startDate - input
   * @param endDate   - input
   * @return boolean
   */
  public static boolean validateStartDateAndEndDate(Integer startDate, Integer endDate) {
    if (startDate == null) {
      if (endDate != null) {
        return validateDate(endDate);
      } else {
        return true;
      }
    } else {
      if (endDate == null) {
        return validateDate(startDate);
      } else {
        return validateDate(startDate) && validateDate(endDate) && (startDate > endDate);
      }
    }
  }
}
