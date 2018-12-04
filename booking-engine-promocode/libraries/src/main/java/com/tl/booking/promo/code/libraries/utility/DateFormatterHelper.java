package com.tl.booking.promo.code.libraries.utility;

import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateFormatterHelper {

  private DateFormatterHelper() {
    // constructors class
  }

  public static Date stringToDate(String date, Integer offset) {
    String pattern = "";
    ZoneOffset zoneOffset = ZoneOffset.ofHours(offset);
    if (date.length() == 19) {
      pattern = "yyyy-MM-dd HH:mm:ss";
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
      LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
      return Date.from(dateTime.atZone(ZoneId.ofOffset("GMT", zoneOffset)).toInstant());
    } else if (date.length() == 10) {
      pattern = "yyyy-MM-dd";

      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(pattern, Locale.ENGLISH);

      LocalDate localDate = LocalDate.parse(date, formatter);

      return Date.from(localDate.atStartOfDay(ZoneId.ofOffset("GMT", zoneOffset)).toInstant());

    } else {
      throw new BusinessLogicException(ResponseCode.DATE_NOT_VALID.getCode(),
          ResponseCode.DATE_NOT_VALID.getMessage());
    }
  }

  public static Date getTodayDate() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR, 00);
    cal.set(Calendar.MINUTE, 00);
    cal.set(Calendar.SECOND, 00);
    cal.set(Calendar.MILLISECOND, 00);
    cal.set(Calendar.HOUR_OF_DAY, 00);

    return cal.getTime();
  }

  public static Date getTodayDateTime() {
    return new DateTime().withZone(DateTimeZone.UTC).toDate();
  }

  public static String dateToString(Date date, String pattern) {
    DateFormat dateFormat = new SimpleDateFormat(pattern);

    return dateFormat.format(date);
  }
}
