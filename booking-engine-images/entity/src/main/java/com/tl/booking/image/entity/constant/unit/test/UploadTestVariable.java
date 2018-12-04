package com.tl.booking.image.entity.constant.unit.test;

import com.tl.booking.image.entity.PhotoFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadTestVariable {

  public static String PHOTO_FILE = "photoFile123.jpg";
  public static String PHOTO_PATH_URL = "assets/photo/";

  public static String YEAR = "2018";
  public static String MONTH = "02";
  public static String DAY = "02";
  public static String PATH_DATE = YEAR +"/"+ MONTH +"/"+ DAY +"/";
  public static String DATA_BASE64 = "dGVzdA==";
  public static String PROPERTY = "w_100,h_100,type_rat-res,wr_1000,q_10";
  public static String PROPERTY_RAT = "w_100,h_100,type_rat,wr_1000,q_10";
  public static String PROPERTY_WITH_CRO = "w_100,h_100,type_cro,q_10";
  public static String PROPERTY_WITHOUT_CRO = "w_100,h_100,type_rat,q_10";
  public static String PROPERTY_WITHOUT_WIDTH = "w_100,type_cro,q_10";
  public static String PROPERTY_RES_WITHOUT_WIDTH = "w_100,type_res,q_10";
  public static String PROPERTY_RES_Q_100 = "wr_100,type_rat,q_100";
  public static String PROPERTY_RES_CRO = "w_100,h_100,type_res-cro,wr_1000,q_10";
  public static String PROPERTY_RES_RAT = "w_100,h_100,type_res-rat,wr_1000,q_10";

  public static PhotoFile PHOTO_FILE_OBJECT = setPhotoFile();
  public static String HOST_PHOTO = "http://localhost/";
  public static String PHOTO_PATH = "assets/photo/";

  public static PhotoFile setPhotoFile() {

    PhotoFile photoFile = new PhotoFile();
    photoFile.setName(PHOTO_FILE);
    photoFile.setData(DATA_BASE64);

    return photoFile;
  }

  public static String IMAGE_DATE_PATH = image_date();

  static String image_date() {
    return YEAR + "/" + MONTH + "/" + DAY + "/";
  }

  public static Map<String, Map<String, Object>> FILE_PROPERTY_READ = filePropertyRead(PROPERTY);
  public static Map<String, Map<String, Object>> FILE_PROPERTY_READ_WITH_CRO = filePropertyRead(PROPERTY_WITH_CRO);
  public static Map<String, Map<String, Object>> FILE_PROPERTY_READ_WITHOUT_CRO = filePropertyRead(PROPERTY_WITHOUT_CRO);
  public static Map<String, Map<String, Object>> FILE_PROPERTY_READ_WITHOUT_WIDTH = filePropertyRead(PROPERTY_WITHOUT_WIDTH);
  public static Map<String, Map<String, Object>> FILE_PROPERTY_READ_RES_WITHOUT_WIDTH = filePropertyRead(PROPERTY_RES_WITHOUT_WIDTH);
  public static Map<String, Map<String, Object>> FILE_PROPERTY_READ_RES_Q_100 = filePropertyRead(PROPERTY_RES_Q_100);
  public static Map<String, Map<String, Object>> FILE_PROPERTY_RES_CRO = filePropertyRead(PROPERTY_RES_CRO);
  public static Map<String, Map<String, Object>> FILE_PROPERTY_RES_RAT = filePropertyRead(PROPERTY_RES_RAT);

  public static Map<String, Map<String, Object>> FILE_PROPERTY_RAT = filePropertyRead(PROPERTY_RAT);


  public static Map<String, Map<String, Object>> filePropertyRead(String fileName)
  {
    Map<String, Map<String, Object>> paramProperties = new HashMap<>();
    Map<String, Object> dimension = new HashMap<>();
    Map<String, Object> compression = new HashMap<>();

    String[] paramConfig = fileName.split(",");

    for (String getParamConfig: paramConfig) {
      String[] properties = getParamConfig.split("_");
      if(properties[0].equals("wr"))
      {
        Map<String, Object> wr = new HashMap<>();
        wr.put("value", properties[1]);
        paramProperties.put("wr", wr);
      }
      else if(properties[0].equals("w"))
      {
        dimension.put("w", properties[1]);
      }
      else if(properties[0].equals("h"))
      {
        dimension.put("h", properties[1]);
      }
      else if(properties[0].equals("q"))
      {
        compression.put("q", properties[1]);
      }
    }

    paramProperties.put("dimension", dimension);
    paramProperties.put("compression", compression);

    return paramProperties;
  }

  public static List<String> TYPES = generateType(PROPERTY);
  public static List<String> TYPES_WITH_CRO = generateType(PROPERTY_WITH_CRO);
  public static List<String> TYPES_WITHOUT_CRO = generateType(PROPERTY_WITHOUT_CRO);
  public static List<String> TYPES_WITHOUT_WIDTH = generateType(PROPERTY_WITHOUT_WIDTH);
  public static List<String> TYPES_RES_WITHOUT_WIDTH = generateType(PROPERTY_RES_WITHOUT_WIDTH);
  public static List<String> TYPES_RES_Q_100 = generateType(PROPERTY_RES_Q_100);
  public static List<String> TYPES_RES_CRO = generateType(PROPERTY_RES_CRO);
  public static List<String> TYPES_RES_RAT = generateType(PROPERTY_RES_RAT);
  public static List<String> TYPES_RAT = generateType(PROPERTY_RAT);

  public static List<String> generateType(String fileName)
  {
    String[] paramConfig = fileName.split(",");
    List<String> types = new ArrayList<>();
    for (String getParamConfig: paramConfig) {
      String[] properties = getParamConfig.split("_");
      if(properties[0].equals("type"))
      {
        String[] propertiesType = properties[1].split("-");
        for (String getPropertiesType: propertiesType) {
          types.add(getPropertiesType);
        }
      }
    }

    return types;
  }


}
