package com.tl.booking.image.service.impl;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.image.dao.api.ImagePropertiesRepository;
import com.tl.booking.image.dao.api.ImageRepository;
import com.tl.booking.image.dao.api.SystemParameterRepository;
import com.tl.booking.image.entity.DateFormatImage;
import com.tl.booking.image.entity.ImagesRequest;
import com.tl.booking.image.entity.PhotoFile;
import com.tl.booking.image.entity.constant.enums.EncryptionType;
import com.tl.booking.image.entity.constant.enums.PhotoPromoPage;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.constant.fields.MandatoryFields;
import com.tl.booking.image.entity.dao.Image;
import com.tl.booking.image.entity.dao.ImageProperties;
import com.tl.booking.image.entity.dao.SystemParameter;
import com.tl.booking.image.libraries.configuration.AssetsFileProperties;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.libraries.utility.DateFormatterHelper;
import com.tl.booking.image.libraries.utility.FileHelper;
import com.tl.booking.image.libraries.utility.SecurityHelper;
import com.tl.booking.image.service.api.CloudinaryService;
import com.tl.booking.image.service.api.ImageService;
import com.tl.booking.image.service.api.UploadService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private ImagePropertiesRepository imagePropertiesRepository;

  @Autowired
  private SystemParameterRepository systemParameterRepository;

  @Autowired
  private UploadService uploadService;

  @Autowired
  private CloudinaryService cloudinaryService;

  @Autowired
  private AssetsFileProperties assetsFileProperties;

  private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

  public Image createImage(Image imageParam, ImagesRequest imagesRequest) {

    String nameImage = this.setNameFile(imageParam.getName());

    String photoName = FileHelper.generateNamePhoto(nameImage);
    String photoData = imagesRequest.getData();
    imagesRequest.setName(photoName);

    this.validateDataImageExtension(photoName);

    imageParam.setIsDeleted(0);

    Date dateNow = new Date();
    String pathDate = DateFormatterHelper.convertDateToString(dateNow, "yyyy/MM/dd/");

    PhotoFile photoFile = new PhotoFile();
    photoFile.setName(photoName);
    photoFile.setData(photoData);

    String urlUploadedImage = cloudinaryService.uploadImage(pathDate,imagesRequest);
    LOGGER.info("URL Cloudinary -> " + urlUploadedImage);

    imageParam.setName(pathDate + photoName);
    imageParam.setUrlCloudinary(urlUploadedImage);
    Image createdImage = this.imageRepository.save(imageParam);

    Image newImage = toMapperImage(createdImage);
    newImage.setName(urlUploadedImage);

    return newImage;

  }

  @Override
  public Image createImageFromURL(Image imageParam, ImagesRequest imageRequest) {

    String nameImage = this.setNameFile(imageParam.getName());

    imageParam.setName(nameImage);

    String photoName = FileHelper.generateNamePhoto(imageParam.getName());
    PhotoFile photoFile;

    URL url = FileHelper.stringToUrl(imageRequest.getData());
    photoFile = FileHelper.getPhotoDataFromUrl(url, photoName);
    imageRequest.setData(photoFile.getData());
    imageRequest.setName(photoName);

    this.validateDataImageExtension(photoFile.getName());

    Date dateNow = new Date();

    String pathDate = DateFormatterHelper.convertDateToString(dateNow, "yyyy/MM/dd/");

    String urlUploadedImage = cloudinaryService
        .uploadImage( pathDate, imageRequest);
    LOGGER.info("URL Cloudinary -> " + urlUploadedImage);

    String imageUrl = pathDate + photoFile.getName();
    imageParam.setName(imageUrl);
    imageParam.setUrlCloudinary(urlUploadedImage);

    imageRepository.save(imageParam);

    Image newImage = new Image();
    newImage.setName(urlUploadedImage);

    return newImage;
  }

  @Override
  public Boolean reUploadImageToCloudinary() {
    List<Image> images = imageRepository.findAll();
    Boolean result = false;
    List<Image> uploadedImages = images.stream().map(image -> {
      File imageFile = new File(assetsFileProperties.getPhoto() + image.getName());

      String urlUploadedImage = cloudinaryService.uploadImageFromFile(imageFile, image.getName());
      image.setUrlCloudinary(urlUploadedImage);

      return image;
    }).collect(Collectors.toList());
    if(!Objects.isNull(uploadedImages))
    {
      imageRepository.save(uploadedImages);
      result=true;
    }
    return result;
  }

  private String setNameFile(String name) {
    String[] nameArray = name.split("\\.");
    Integer countNameArray = nameArray.length;
    String extension = nameArray[countNameArray - 1];

    return SecurityHelper.encrypt(name, EncryptionType.MD5.getValue()) + "." + extension;
  }

  public String getImage(DateFormatImage dateFormatImage, String imageNameFile) {

    String[] imageNameArray = imageNameFile.split("\\.");
    Integer countImageName = imageNameArray.length;

    if (countImageName < 2 || countImageName > 3) {
      throw new BusinessLogicException(ResponseCode.FILE_NAME_IMAGE_INVALID.getCode(),
          ResponseCode.FILE_NAME_IMAGE_INVALID.getMessage());
    }

    String imageName = "";
    String imageProperty = "";
    String original = "";

    if (countImageName == 3) {
      imageName = imageNameArray[countImageName - 2] + "." + imageNameArray[countImageName - 1];
      imageProperty = imageNameArray[0];
    } else if (countImageName == 2) {
      imageName = imageNameFile;
    }

    String getDateFormatImage = this.generatePathImageDate(dateFormatImage);

    String urlImage = parseNameImage(getDateFormatImage, imageProperty, imageName);

    Image image = this.imageRepository
        .findImageByNameAndIsDeleted(getDateFormatImage + imageName, 0);

    if (!isExistImageStore(image)) {
      throw new BusinessLogicException(ResponseCode.IMAGE_NOT_FOUND.getCode(),
          ResponseCode.IMAGE_NOT_FOUND.getMessage());
    }

    if (imageProperty.equals(original)) {
      return assetsFileProperties.getPhoto() + getDateFormatImage + imageName;
    }

    ImageProperties imageProperties = this.imagePropertiesRepository
        .findByPropertiesAndIsDeleted(imageProperty, 0);

    if (!isExistImageProperties(imageProperties)) {
      throw new BusinessLogicException(ResponseCode.PROPERTY_IMAGE_NOT_EXIST.getCode(),
          ResponseCode.PROPERTY_IMAGE_NOT_EXIST.getMessage());
    }

    this.uploadService.generateImage(getDateFormatImage, imageProperty, imageName);

    File getConfigImage = new File(assetsFileProperties.getPhoto() + urlImage);

    if (!isExistImage(getConfigImage)) {
      throw new BusinessLogicException(ResponseCode.IMAGE_NOT_FOUND_IN_ASSETS.getCode(),
          ResponseCode.IMAGE_NOT_FOUND_IN_ASSETS.getMessage());
    }

    return assetsFileProperties.getPhoto() + urlImage;
  }

  public String getImageCloudinary(DateFormatImage dateFormatImage, String imageNameFile) {

    String urlCloudinary = "";
    String[] imageNameArray = imageNameFile.split("\\.");
    Integer countImageName = imageNameArray.length;

    if (countImageName < 2 || countImageName > 3) {
      throw new BusinessLogicException(ResponseCode.FILE_NAME_IMAGE_INVALID.getCode(),
          ResponseCode.FILE_NAME_IMAGE_INVALID.getMessage());
    }

    String imageName = "";
    String imageProperty = "";
    if (countImageName == 3) {
      imageName = imageNameArray[countImageName - 2] + "." + imageNameArray[countImageName - 1];
      imageProperty = imageNameArray[0];
    } else if (countImageName == 2) {
      imageName = imageNameFile;
    }

    String getDateFormatImage = this.generatePathImageDate(dateFormatImage);
    Image image = this.imageRepository
        .findImageByNameAndIsDeleted(getDateFormatImage + imageName, 0);

    if (!isExistImageStore(image)) {
      throw new BusinessLogicException(ResponseCode.IMAGE_NOT_FOUND.getCode(),
          ResponseCode.IMAGE_NOT_FOUND.getMessage());
    }

    String original = "";

    if (imageProperty.equals(original)) {
      String urlCloudinaryOriginal = cloudinaryService.getImagesOriginal(imageName);
      LOGGER.info("URL Cloudinary-> " + urlCloudinaryOriginal);
      return urlCloudinaryOriginal;
    }

    ImageProperties imageProperties = this.imagePropertiesRepository
        .findByPropertiesAndIsDeleted(imageProperty, 0);

    if (!isExistImageProperties(imageProperties)) {
      throw new BusinessLogicException(ResponseCode.PROPERTY_IMAGE_NOT_EXIST.getCode(),
          ResponseCode.PROPERTY_IMAGE_NOT_EXIST.getMessage());
    }

    Map<String, Map<String, Object>> generateFileName = FileHelper.filePropertyRead(imageProperty);

    Integer quality = this.getQuality(generateFileName);

    List<String> types = FileHelper.generateType(imageProperty);
    for (String getType : types) {
      String rat = "rat";
      String res = "res";
      String cro = "cro";

      Object dimensionWidth = generateFileName.get("dimension").get("w");
      Object dimensionHeight = generateFileName.get("dimension").get("h");

      if (getType.equals(rat)) {

        urlCloudinary = this.getRatioType(generateFileName, quality, imageName);
      } else if (getType.equals(res)) {
        urlCloudinary = this.getResizeType(dimensionWidth, dimensionHeight, quality, imageName);
      } else if (getType.equals(cro)) {
        urlCloudinary = this.getCropType(dimensionWidth, dimensionHeight, quality, imageName);
      }
    }
    LOGGER.info("URL Cloudinary-> " + urlCloudinary);
    return urlCloudinary;
  }

  private Image toMapperImage(Image image) {
    return BeanMapper.map(image, Image.class);
  }

  private boolean isExistImageStore(Image image) {
    return image != null;
  }


  public String getRatioType(Map<String, Map<String, Object>> generateFileName, Integer quality,
      String imageName) {

    if (generateFileName.get("wr") == null) {
      throw new BusinessLogicException(ResponseCode.WIDTH_RATIO_NOT_EXIST.getCode(),
          ResponseCode.WIDTH_RATIO_NOT_EXIST.getMessage());
    }

    String wr = (String) generateFileName.get("wr").get("value");
    String urlCloudinary = cloudinaryService.imageTransformationRatio(wr, imageName);
    return urlCloudinary;

  }

  public String getResizeType(Object widthRequest, Object heightRequest, Integer quality,
      String imageName) {

    if (widthRequest == null || heightRequest == null) {
      throw new BusinessLogicException(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getCode(),
          ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getMessage());
    }

    String width = (String) widthRequest;
    String height = (String) heightRequest;
    String urlCloudinary = cloudinaryService
        .imageTransformationResize(width, height, imageName);
    return urlCloudinary;
  }

  public String getCropType(Object widthRequest, Object heightRequest, Integer quality,
      String imageName) {
    if (widthRequest == null || heightRequest == null) {
      throw new BusinessLogicException(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getCode(),
          ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getMessage());
    }

    String width = (String) widthRequest;
    String height = (String) heightRequest;
    String urlCloudinary = cloudinaryService
        .imageTransformationCropCenter(width, height, imageName);
    return urlCloudinary;
  }

  public ResponseEntity<InputStreamResource> outputStreamFromURL(String image) {
    InputStream getImage = null;
    try {
      getImage = new URL(image).openStream();

    } catch (IOException e) {
      LOGGER.info("Cannot Input Stream Resource ", e);
      throw new BusinessLogicException(ResponseCode.CANNOT_INPUT_STREAM_RESOURCE.getCode(),
          ResponseCode.CANNOT_INPUT_STREAM_RESOURCE.getMessage());
    }

    return ResponseEntity
        .ok()
        .eTag(assetsFileProperties.geteTagVersion())
        .contentType(MediaType.IMAGE_PNG)
        .body(new InputStreamResource(getImage));
  }

  public Integer getQuality(Map<String, Map<String, Object>> generateFileName) {
    Integer quality = 0;
    if (isExistCompression(generateFileName.get("compression").get("q"))) {
      Integer inputQuality = isNullObjectInteger(generateFileName.get("compression").get("q"));
      quality = inputQuality * 10;
    }
    return quality;
  }

  private Boolean isExistCompression(Object o) {
    return o != null;
  }

  private Integer isNullObjectInteger(Object o) {
    return o == null ? 10 : Integer.parseInt((String) o);
  }

  public ResponseEntity<InputStreamResource> outputStream(String image) {
    InputStream getImage = null;
    try {
      File getImagePath = new File(image);
      getImage = new FileInputStream(getImagePath);
    } catch (FileNotFoundException e) {
      LOGGER.info("Cannot Input Stream Resource ", e);
      throw new BusinessLogicException(ResponseCode.CANNOT_INPUT_STREAM_RESOURCE.getCode(),
          ResponseCode.CANNOT_INPUT_STREAM_RESOURCE.getMessage());
    }

    return ResponseEntity
        .ok()
        .eTag(assetsFileProperties.geteTagVersion())
        .contentType(MediaType.IMAGE_PNG)
        .body(new InputStreamResource(getImage));
  }

  public void removeFileProperty(String image) {
    try {
      String[] imageNameArray = image.split("\\.");
      Integer countImageName = imageNameArray.length;

      if (countImageName == 3) {
        Path path = Paths.get(image);

        Files.deleteIfExists(path);
      }
    } catch (IOException e) {

      LOGGER.info("Image Not Exist ", e);
      throw new BusinessLogicException(ResponseCode.IMAGE_NOT_FOUND_IN_ASSETS.getCode(),
          ResponseCode.IMAGE_NOT_FOUND_IN_ASSETS.getMessage());
    }
  }

  private Boolean isExistImageProperties(ImageProperties imageProperties) {
    return imageProperties != null;
  }

  private Boolean isExistImage(File urlImage) {
    return urlImage.exists();
  }

  private String parseNameImage(String getDateFormatImage, String imageProperty, String imageName) {
    return getDateFormatImage + imageProperty + "." + imageName;
  }

  private String generatePathImageDate(DateFormatImage dateFormatImage) {
    return dateFormatImage.getYear() + "/" + dateFormatImage.getMonth() + "/" + dateFormatImage
        .getDay() + "/";
  }


  private void validateDataImageExtension(String image) {

    SystemParameter systemParameter = this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable((String) MDC.get(MandatoryFields.STORE_ID),
            PhotoPromoPage.PHOTO_EXTENSION.getName());

    if (isNullSystemParameter(systemParameter)) {
      throw new BusinessLogicException(ResponseCode.MERCHANT_PHOTO_NOT_EXIST.getCode(),
          ResponseCode.MERCHANT_PHOTO_NOT_EXIST.getMessage());
    }

    String value = systemParameter.getValue();
    String[] arrayValue = value.split(",");

    Boolean statusMatchExtension = false;
    for (String valueData : arrayValue) {
      String valueDataParse = "." + valueData.toLowerCase();

      if (isMatchExtension(valueDataParse, image)) {
        statusMatchExtension = true;
        break;
      }

    }

    if (!statusMatchExtension) {
      throw new BusinessLogicException(ResponseCode.PHOTO_EXTENSION_NOT_MATCH.getCode(),
          ResponseCode.PHOTO_EXTENSION_NOT_MATCH.getMessage());
    }

  }

  private boolean isNullSystemParameter(SystemParameter systemParameter) {
    return systemParameter == null;
  }


  private Boolean isMatchExtension(String dataParse, String photo) {
    return photo.contains(dataParse);
  }


}
