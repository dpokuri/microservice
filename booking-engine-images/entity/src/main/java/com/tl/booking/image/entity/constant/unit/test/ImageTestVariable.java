package com.tl.booking.image.entity.constant.unit.test;

import com.tl.booking.image.entity.DateFormatImage;
import com.tl.booking.image.entity.DateFormatImageBuilder;
import com.tl.booking.image.entity.ImagesRequest;
import com.tl.booking.image.entity.ImagesRequestBuilder;
import com.tl.booking.image.entity.PhotoFile;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.dao.Image;
import com.tl.booking.image.entity.dao.ImageBuilder;
import com.tl.booking.image.entity.dao.ImageProperties;
import com.tl.booking.image.entity.dao.ImagePropertiesBuilder;
import com.tl.booking.image.entity.dao.SystemParameter;
import com.tl.booking.image.entity.dao.SystemParameterBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.joda.time.DateTime;

public class ImageTestVariable {

  public static String VARIABLE_STRING = "photoExtension";
  public static String DESCRIPTION = "desc123";
  public static String VALUE = "jpg,jpeg,png";
  public static String VALUE_OUTER_EXTENSION = "nana,nono";

  public static Date DATE_NOW = new DateTime().toDate();
  public static DateTime DATE_TIME = new DateTime();

  public static String PHOTO_PATH = "assets/photo/";
  public static String PHOTO_PATH_URL = "assets/photo/";
  public static String HOST_PHOTO = "http://localhost/";

  public static String DATE_PATTERN = "yyyy/MM/dd/";
  public static String ID = "5a4b8338eaa1e124cf1dd789";
  public static String USERNAME = "yonathan";
  public static String CHANNEL_ID = "1";

  public static String NAME_IMAGE_STRING = "name123.jpg";
  public static String NAME_IMAGE_STRING_FOR_URL = "name123.jpg";
  public static String NAME_IMAGE_MD5 = "sdbfashjhsafasf";

  public static String DATA_BASE64 = "DataPhoto";
  public static String IMAGE_ENCODE="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";
  public static String STRING_URL = "https://www.google.co.id/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
  public static String STRING_URL_NOT_VALID = "https://www.google.co.id/images/branding/googleloo/2x/googlelogo_color_272x92dp.png";
  public static URL IMAGE_URL = stringToUrl(STRING_URL);

  public static String NAME_IMAGE_STRING_NotValidException = "name123.jpg";
  public static String FOLDER = "flight";
  public static String NAME_IMAGE_STRING_PhotoExtensionNotmatch = "name123.jpg";

  public static String WIDTH = "200";
  public static String WR = "2000";
  public static String HEIGHT = "496";
  public static String QUALITY_CLOUDINARY = "100";
  public static String IMAGE_NAME_CLOUDINARY = "107130520186f61d0da2031a2c613a6438314e1d930";
  public static String URL_IMAGE_CLOUDINARY = "http://res.cloudinary.com/dbpw31rhm/image/upload/c_thumb,g_center,h_250,q_100,w_1000/"+IMAGE_NAME_CLOUDINARY+".jpg";
  public static String URL_UPLOADED_IMAGE = "http://res.cloudinary.com/dbpw31rhm/image/upload/flight/"+IMAGE_NAME_CLOUDINARY;
  public static String URL_IMAGE_CLOUDINARY_EXCEPTION = "http://res.cloudinary.com/image/upload/c_thumb,g_center,h_250,q_100,w_1000/"+IMAGE_NAME_CLOUDINARY+".jpg";
  public static String URL_IMAGE_RES_CLOUDINARY ="http://res.cloudinary.com/dbpw31rhm/image/upload/h_500,q_100,w_2000/107130520186f61d0da2031a2c613a6438314e1d930.jpg";
  public static String URL_ORIGINAL_IMAGE_CLOUDINARY = "http://res.cloudinary.com/dbpw31rhm/image/upload/flight/"+IMAGE_NAME_CLOUDINARY;
  public static String URL_RATIO_IMAGE_CLOUDINARY ="http://res.cloudinary.com/dbpw31rhm/image/upload/c_scale,q_100,w_2000/107130520186f61d0da2031a2c613a6438314e1d930.jpg";
  public static String CONVERT_DATE_TO_STRING = convertDateToString(DATE_NOW, DATE_PATTERN);
  public static String PROPERTY_RAT_WITHOUT_WR = "wr_2000,type_rat,q_10";
  public static String PROPERTY_IMAGE_ENCODE = "data:image/png;base64,";
  public static String PROPERTY_CLOUDINARY = "type_cro,w_1000,h_250,q_10";
  public static String PROPERTY_RES_CLOUDINARY ="type_res,w_2000,h_500,q_10";

  public static String urlImage = generateNamePhoto(NAME_IMAGE_STRING);
  public static String IMAGE_STRING_GENERATE = "generateimage.jpg";

  public static PhotoFile PHOTO_FILE = setPhotoFile();

  public static PhotoFile setPhotoFile() {

    PhotoFile photoFile = new PhotoFile();
    photoFile.setName(IMAGE_STRING_GENERATE);
    photoFile.setData(IMAGE_ENCODE);

    return photoFile;
  }

  public static Image IMAGE_CREATE_CONTROLLER = new ImageBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(urlImage)
      .build();


  public static Image IMAGE_CREATE_SERVICE_RESPONSE = new ImageBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(HOST_PHOTO + PHOTO_PATH_URL + urlImage)
      .build();

  public static Image IMAGE_GET_SERVICE_RESPONSE = new ImageBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(HOST_PHOTO + PHOTO_PATH_URL + urlImage)
      .build();

  public static String generateNamePhoto(String name)
  {
    return (new Random().nextInt(1000) + 1) + new SimpleDateFormat("ddMMyyyy").format(DATE_TIME.toDate()) + name;
  }


  public static Image IMAGE_CREATE_RESULT = new ImageBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(HOST_PHOTO + PHOTO_PATH_URL + CONVERT_DATE_TO_STRING + "original/" + NAME_IMAGE_STRING)
      .build();

  public static Image IMAGE = new ImageBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(NAME_IMAGE_STRING)
      .build();


  public static Image IMAGE_NotValidException = new ImageBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(NAME_IMAGE_STRING_NotValidException)
      .build();

  public static Image IMAGE_PhotoExtensionNotmatch = new ImageBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(NAME_IMAGE_STRING_PhotoExtensionNotmatch)
      .build();

  public static SystemParameter CREATE_SYSTEM_PARAMETER = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withValue(VALUE).withDescription(DESCRIPTION).withVariable(VARIABLE_STRING)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withIsDeleted(0)

      .build();

  public static SystemParameter CREATE_SYSTEM_PARAMETER_CREATE = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withValue(VALUE).withDescription(DESCRIPTION).withVariable(VARIABLE_STRING)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withIsDeleted(0)

      .build();

  public static SystemParameter SYSTEM_PARAMETER_OUTER_EXTENSION = new SystemParameterBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withValue(VALUE_OUTER_EXTENSION).withDescription(DESCRIPTION).withVariable(VARIABLE_STRING)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withIsDeleted(0)

      .build();



  public static URL stringToUrl(String stringUrl){
    URL url = null;
    try {
      url = new URL(stringUrl);
    } catch (IOException ioe){
      System.out.println(ioe);
    }

    return url;
  }


  public static String convertDateToString(Date indate, String pattern)
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


  static Date stringToDate(String date) {
    String pattern = "";
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
    }
    return null;
  }

  public static String PROPERTIES = "propertiesOne";
  public static String IMAGE_NAME = "imageName.jpg";
  public static String IMAGE_NAME_INVALID = "sdsd.asdasds.imageName.jpg";
  public static String YEAR = "2018";
  public static String MONTH = "05";
  public static String DAY = "13";


  public static ImageProperties IMAGE_PROPERTIES = new ImagePropertiesBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)

      .withProperties(PROPERTIES)
      .build();

  public static Image IMAGE_FOR_GET = new ImageBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(CONVERT_DATE_TO_STRING + "original/" + NAME_IMAGE_STRING)
      .build();

  public static Image IMAGE_FOR_GET_ORIGINAL = new ImageBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(CONVERT_DATE_TO_STRING + NAME_IMAGE_STRING)
      .build();


  public static DateFormatImage dateFormatImage = new DateFormatImageBuilder()
      .withYear(YEAR)
      .withMonth(MONTH)
      .withDay(DAY)
      .build();
  public static String getDateFormatImage = generatePathImageDate(dateFormatImage);

  public static String URL_IMAGE = parseNameImage(getDateFormatImage, PROPERTIES, IMAGE_NAME);
  static String parseNameImage(String getDateFormatImage, String imageProperty, String imageName)
  {
    return getDateFormatImage + imageProperty + "." + imageName;
  }

  static String generatePathImageDate(DateFormatImage dateFormatImage) {
    return dateFormatImage.getYear()+"/"+dateFormatImage.getMonth()+"/"+dateFormatImage.getDay()+"/";
  }


  public static String CONVERT_DATE_TO_STRING_CREATE = getDateFormatImage;

  public static Image IMAGE_FOR_CREATE = new ImageBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(NAME_IMAGE_STRING)
      .build();

  public static Image IMAGE_CREATE_SERVICE_REQUEST = new ImageBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(ImageTestVariable.PHOTO_PATH_URL + ImageTestVariable.HOST_PHOTO + getDateFormatImage + IMAGE_STRING_GENERATE)
      .build();

  public static Image IMAGE_CREATE_SERVICE = new ImageBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(getDateFormatImage + IMAGE_STRING_GENERATE)
      .withUrlCloudinary(URL_UPLOADED_IMAGE)
      .build();

  public static Image IMAGE_CREATED = new ImageBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(getDateFormatImage + IMAGE_STRING_GENERATE)
      .withUrlCloudinary(URL_UPLOADED_IMAGE)
      .build();

  public static Image IMAGE_CREATE_SERVICE_2 = new ImageBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(getDateFormatImage + IMAGE_STRING_GENERATE)
      .withUrlCloudinary(URL_UPLOADED_IMAGE)
      .build();

  public static Image IMAGE_CREATE_SERVICE_VERIFY = new ImageBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(HOST_PHOTO + getDateFormatImage + IMAGE_STRING_GENERATE)
      .build();

  public static Image IMAGE_FOR_CREATE_SERVICE = new ImageBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(NAME_IMAGE_STRING)
      .build();

  public static Image IMAGE_PARAM = new ImageBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(NAME_IMAGE_STRING_FOR_URL)
      .build();

  public static Image IMAGE_FROM_FILE = new ImageBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(IMAGE_NAME_CLOUDINARY)
      .build();

  public static Image IMAGE_FROM_FILE_RESULT = new ImageBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(DATE_TIME.toDate())
      .withUpdatedDate(DATE_TIME.toDate())
      .withIsDeleted(0)
      .withName(IMAGE_NAME_CLOUDINARY)
      .withUrlCloudinary(URL_UPLOADED_IMAGE)
      .build();


  public static ImageProperties IMAGE_PROPERTIES_CRO_CLOUDINARY = new ImagePropertiesBuilder()
            .withStoreId(CommonTestVariable.STORE_ID)
            .withCreatedBy(USERNAME)
            .withUpdatedBy(USERNAME)
            .withChannelId(CHANNEL_ID)
            .withCreatedDate(DATE_TIME.toDate())
            .withUpdatedDate(DATE_TIME.toDate())
            .withIsDeleted(0)

            .withProperties(PROPERTY_CLOUDINARY)
            .build();

    public static ImageProperties IMAGE_PROPERTIES_RES_CLOUDINARY = new ImagePropertiesBuilder()
            .withStoreId(CommonTestVariable.STORE_ID)
            .withCreatedBy(USERNAME)
            .withUpdatedBy(USERNAME)
            .withChannelId(CHANNEL_ID)
            .withCreatedDate(DATE_TIME.toDate())
            .withUpdatedDate(DATE_TIME.toDate())
            .withIsDeleted(0)

            .withProperties(PROPERTY_RES_CLOUDINARY)
            .build();

    public static ImageProperties IMAGE_PROPERTIES_RAT_CLOUDINARY = new ImagePropertiesBuilder()
            .withStoreId(CommonTestVariable.STORE_ID)
            .withCreatedBy(USERNAME)
            .withUpdatedBy(USERNAME)
            .withChannelId(CHANNEL_ID)
            .withCreatedDate(DATE_TIME.toDate())
            .withUpdatedDate(DATE_TIME.toDate())
            .withIsDeleted(0)

            .withProperties(PROPERTY_RAT_WITHOUT_WR)
            .build();

  public static String CREATE_REQUEST_BODY_JSON = "{\n"
      + "  \"data\": \""+DATA_BASE64+"\",\n"
      + "  \"folder\": \""+FOLDER+"\",\n"
      + "  \"name\": \""  + NAME_IMAGE_STRING + "\"\n"
      + "}";


  public static String getDateFormatImageCreate = generatePathImageDate(dateFormatImage);
  public static String CONVERT_DATE_TO_STRING_CREATE_SERVICE = getDateFormatImageCreate;
  public static List<Image> IMAGES = Arrays.asList(IMAGE_FROM_FILE);
  public static List<Image> IMAGE_FROM_FILES = Arrays.asList(IMAGE_FROM_FILE_RESULT);
  public static Boolean IMAGE_UPLOADED = true;

  public static File FILE = new File(PHOTO_PATH+IMAGE_NAME_CLOUDINARY);

  public static ImagesRequest IMAGE_REQUEST=  new ImagesRequestBuilder()
      .withData(IMAGE_ENCODE)
      .withName(IMAGE_STRING_GENERATE)
      .withFolder(FOLDER)
      .build();

  public static ImagesRequest IMAGE_REQUEST_URL=  new ImagesRequestBuilder()
      .withData(STRING_URL)
      .withName(IMAGE_STRING_GENERATE)
      .withFolder(FOLDER)
      .build();

}
