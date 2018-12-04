package com.tl.booking.image.service.impl;


import com.tl.booking.image.entity.PhotoFile;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.libraries.configuration.AssetsFileProperties;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.libraries.utility.FileHelper;
import com.tl.booking.image.service.api.UploadService;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl implements UploadService {

  @Autowired
  AssetsFileProperties assetsFileProperties;

  @Override
  public void createdPhoto(PhotoFile photoFile, String pathImageLocation, String pathDate) {

    Integer maxFileSize = assetsFileProperties.getMaxFileSize();

    this.checkFileSize(photoFile, maxFileSize);

    FileHelper.createPhotoFromBase64(photoFile, pathImageLocation, pathDate);
  }

  private void checkFileSize(PhotoFile photoFile, Integer maxFileSize) {
    byte[] valueDecoded = Base64.decodeBase64(photoFile.getData());

    long fileSizeInBytes = valueDecoded.length;
    if (fileSizeInBytes > maxFileSize) {
      throw new BusinessLogicException(ResponseCode.FILE_SIZE_EXCEEDS_MAXIMUM_LIMIT.getCode(),
          ResponseCode.FILE_SIZE_EXCEEDS_MAXIMUM_LIMIT.getMessage());
    }
  }

  @Override
  public void generateImage(String pathImageDate, String property, String name) {
    Map<String, Map<String, Object>> generateFileName = FileHelper.filePropertyRead(property);

    String pathImageLocation = assetsFileProperties.getPhoto();

    String setnameProperty = this.setNameProperty(property);

    String fileNameOrigin = name;
    String fullUrlImage = pathImageLocation + pathImageDate + setnameProperty + name;

    Boolean generateImage = generateImage(generateFileName, fullUrlImage, pathImageLocation,
        setnameProperty, pathImageDate, name, property);

    if (isExistCompression(generateFileName.get("compression").get("q"))) {
      Integer inputQuality = isNullObjectInteger(generateFileName.get("compression").get("q"));
      Float quality = this.setQuality(inputQuality);

      String nameImage = name;
      if (generateImage) {
        nameImage = fullUrlImage;
      }

      this.executeCompress(pathImageLocation, pathImageDate, setnameProperty, nameImage,
          fileNameOrigin, quality, generateImage);
    }
  }

  private Boolean generateImage(Map<String, Map<String, Object>> generateFileName,
      String fullUrlImage, String pathImageLocation, String setnameProperty, String pathImageDate,
      String name, String property) {
    List<String> types = FileHelper.generateType(property);
    return executeFile(generateFileName, types, name, fullUrlImage, pathImageLocation,
        setnameProperty, pathImageDate);
  }

  private Boolean executeFile(Map<String, Map<String, Object>> generateFileName, List<String> types,
      String name, String fullUrlImage, String pathImageLocation, String setnameProperty,
      String pathImageDate) {
    Boolean statusExecuted = false;

    String nameFile = name;

    for (String getType : types) {
      String rat = "rat";
      String res = "res";
      String cro = "cro";

      Object dimensionWidth = generateFileName.get("dimension").get("w");
      Object dimensionHeight = generateFileName.get("dimension").get("h");

      if (getType.equals(rat)) {

        if (statusExecuted.equals(true)) {
          nameFile = fullUrlImage;
        }

        this.generateRatioImage(pathImageLocation, pathImageDate, setnameProperty, nameFile,
            generateFileName, statusExecuted);
        statusExecuted = true;
      } else if (getType.equals(res)) {
        if (statusExecuted.equals(true)) {
          nameFile = fullUrlImage;
        }

        this.generateResizeImage(pathImageLocation, pathImageDate, setnameProperty, nameFile,
            dimensionWidth, dimensionHeight, statusExecuted);
        statusExecuted = true;
      } else if (getType.equals(cro)) {
        if (statusExecuted.equals(true)) {
          nameFile = fullUrlImage;
        }

        this.generateCropImage(pathImageLocation, pathImageDate, setnameProperty, nameFile,
            dimensionWidth, dimensionHeight, statusExecuted);
        statusExecuted = true;
      }
    }

    return statusExecuted;
  }


  private void generateCropImage(String pathImageLocation, String pathImageDate, String property,
      String fileName, Object widthRequest, Object heightRequest, Boolean statusExecuted) {

    if (isNullPropertyObject(widthRequest) || isNullPropertyObject(heightRequest)) {
      throw new BusinessLogicException(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getCode(),
          ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getMessage());
    }

    Integer width = Integer.parseInt((String) widthRequest);
    Integer height = Integer.parseInt((String) heightRequest);

    FileHelper.cropImageCenter(pathImageLocation, pathImageDate, property, fileName, width, height,
        statusExecuted);
  }

  private Boolean isExistCompression(Object o) {
    return o != null;
  }

  private Integer isNullObjectInteger(Object o) {
    return o == null ? 10 : Integer.parseInt((String) o);
  }

  private Float setQuality(Integer inputQuality) {

    Integer setQuality = inputQuality;

    if (setQuality > 10) {
      setQuality = 10;
    }

    Double qualityParse = (double) setQuality / 10;

    return Float.parseFloat(String.valueOf(qualityParse));
  }

  private String setNameProperty(String property) {
    return property + ".";
  }

  private Boolean isNullPropertyObject(Object o) {
    return o == null;
  }

  private void generateRatioImage(String pathImageLocation, String pathImageDate, String property,
      String fileName, Map<String, Map<String, Object>> generateFileName, Boolean statusExecuted) {

    if (isNullPropertyObject(generateFileName.get("wr"))) {
      throw new BusinessLogicException(ResponseCode.WIDTH_RATIO_NOT_EXIST.getCode(),
          ResponseCode.WIDTH_RATIO_NOT_EXIST.getMessage());
    }

    Integer wr = Integer.parseInt((String) generateFileName.get("wr").get("value"));

    FileHelper.ratioImageGenerator(pathImageLocation, pathImageDate, property, fileName, wr,
        statusExecuted);
  }

  private void generateResizeImage(String pathImageLocation, String pathImageDate, String property,
      String fileName, Object widthRequest, Object heightRequest, Boolean statusExecuted) {

    if (isNullPropertyObject(widthRequest) || isNullPropertyObject(heightRequest)) {
      throw new BusinessLogicException(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getCode(),
          ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getMessage());
    }

    Integer width = Integer.parseInt((String) widthRequest);
    Integer height = Integer.parseInt((String) heightRequest);

    FileHelper.resizeImage(pathImageLocation, pathImageDate, property, fileName, width, height,
        statusExecuted);
  }


  private void executeCompress(String pathImageLocation, String pathImageDate, String property,
      String fileName, String fileNameOrigin, Float quality, Boolean statusExecuted) {
    FileHelper.compressImage(pathImageLocation, pathImageDate, property, fileName, fileNameOrigin,
        quality, statusExecuted);
  }

}
