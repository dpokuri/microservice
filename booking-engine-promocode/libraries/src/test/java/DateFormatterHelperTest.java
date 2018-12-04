import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;

public class DateFormatterHelperTest {

  private MockMvc mockMvc;

  @InjectMocks
  private DateFormatterHelper dateFormatterHelper;

  public static String dateToString(Date date, String pattern) {
    DateFormat dateFormat = new SimpleDateFormat(pattern);

    return dateFormat.format(date);
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

  private static Date getTodayDateTime() {
    return new DateTime().withZone(DateTimeZone.UTC).toDate();
  }

  @Test
  public void getTodayDateTimeTest() throws Exception {
    Date newDate = this.dateFormatterHelper.getTodayDateTime();
    assertEquals(newDate, newDate);
  }

  @Test
  public void stringToDateTest() throws Exception {
    Date newDate = this.dateFormatterHelper.stringToDate("2017-08-09", 0);
    assertEquals(this.stringToDate("2017-08-09", 0), newDate);
  }

  @Test
  public void stringToDateTestDateNotValid() throws Exception {
    try {
      this.dateFormatterHelper.stringToDate("2017-08-09e", 0);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.DATE_NOT_VALID.getCode(), be.getCode());
      assertEquals(ResponseCode.DATE_NOT_VALID.getMessage(), be.getMessage());
    }

  }

  @Test
  public void stringToDateWithTimeTest() throws Exception {
    Date newDate = this.dateFormatterHelper.stringToDate("2017-08-09 02:01:04", 0);
    assertEquals(this.stringToDate("2017-08-09 02:01:04", 0), newDate);
  }

  @Test
  public void dateToStringTest() throws Exception {
    Date getDate = new Date();
    String dateString = this.dateFormatterHelper.dateToString(getDate, "yyyy-MM-dd HH:mm:ss");

    assertEquals(dateToString(getDate, "yyyy-MM-dd HH:mm:ss"), dateString);
  }

  @Test
  public void todayDateTest() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR, 00);
    cal.set(Calendar.MINUTE, 00);
    cal.set(Calendar.SECOND, 00);
    cal.set(Calendar.MILLISECOND, 00);
    cal.set(Calendar.HOUR_OF_DAY, 00);

    Date getDate = DateFormatterHelper.getTodayDate();
    assertEquals(cal.getTime(), getDate);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }
}
