import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.libraries.utility.DateFormatterHelper;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;

public class DateFormatterHelperTest {

  private MockMvc mockMvc;

  @InjectMocks
  private DateFormatterHelper dateFormatterHelper;

  @Test
  public void getTodayDateTimeTest() throws Exception {
    Date newDate = this.dateFormatterHelper.getTodayDateTime();
    assertEquals(this.getTodayDateTime(), newDate);
  }


  @Test
  public void stringToDateTest() throws Exception {
    Date newDate = this.dateFormatterHelper.stringToDate("2017-08-09");
    assertEquals(this.generateDate("yyyy-MM-dd", "2017-08-09"), newDate);
  }

  @Test
  public void stringToDateWithTimeTest() throws Exception {
    Date newDate = this.dateFormatterHelper.stringToDate("2017-08-09 02:01:04");
    assertEquals(this.generateDate("yyyy-MM-dd HH:mm:ss","2017-08-09 02:01:04"), newDate);
  }

  @Test
  public void stringToDateWithTimeTestDateNotValid() throws Exception {
    try{
      this.dateFormatterHelper.stringToDate("2017-08-09 00:00:0");
    } catch (BusinessLogicException ble){
      assertEquals(ResponseCode.DATE_NOT_VALID.getCode(), ble.getCode());
    }

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

  @Test
  public void convertDateToStringTest() throws Exception {
    Date dateNow = new Date();
    String toString =   dateFormatterHelper.convertDateToString(dateNow, "yyyy/MM/dd");
    String toStringCompare =   convertDateToString(dateNow, "yyyy/MM/dd");

    assertEquals(toString, toStringCompare);

  }

  @Test
  public void convertDateToStringTestException() throws Exception {
    Date dateNow = null;

    try{
      dateFormatterHelper.convertDateToString(dateNow, "dd/yyyy/MM");
    }
    catch (Exception ex)
    {
      assertEquals(null, ex.getMessage());
    }


  }

  private static String convertDateToString(Date indate, String pattern)
  {
    String dateString = null;
    SimpleDateFormat sdfr = new SimpleDateFormat(pattern);
    /*you can also use DateFormat reference instead of SimpleDateFormat
     * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
     */
    try{
      dateString = sdfr.format( indate );
    }catch (Exception ex ){
      System.out.println(ex);
    }
    return dateString;
  }


  private Date generateDate(String pattern, String date){
    if(date.length() == 19){
      pattern = "yyyy-MM-dd HH:mm:ss";

      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(pattern, Locale.ENGLISH);

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

  private static Date getTodayDateTime() {
    return new Date();
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }



}
