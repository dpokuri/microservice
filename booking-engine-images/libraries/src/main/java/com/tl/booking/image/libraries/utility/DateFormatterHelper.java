package com.tl.booking.image.libraries.utility;

import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateFormatterHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatterHelper.class);

  private DateFormatterHelper() {
  }

  public static Date stringToDate(String date) {
    String pattern = "";
    if(date.length() == 19){
      pattern = "yyyy-MM-dd HH:mm:ss";
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
      LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
      return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    } else if(date.length() == 10){
      pattern = "yyyy-MM-dd";

      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(pattern, Locale.ENGLISH);

      LocalDate localDate = LocalDate.parse(date, formatter);

      return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    } else {
      throw new BusinessLogicException(ResponseCode.DATE_NOT_VALID.getCode(), ResponseCode.DATE_NOT_VALID.getMessage());
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
    return new Date();
  }

  public static String convertDateToString(Date indate, String pattern)
  {
    String dateString = null;
    SimpleDateFormat sdfr = new SimpleDateFormat(pattern);
    try{
      dateString = sdfr.format( indate );
    }catch (Exception ex ){
      LOGGER.error("{} Failed to parse. {}", ex);
    }
    return dateString;
  }

}
