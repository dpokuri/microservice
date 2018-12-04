package com.tl.booking.image.libraries.utility;

import com.tl.booking.image.entity.PhotoFile;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

  static {
    new FileHelper();
  }

  private FileHelper() {

  }

  public static void createPhotoFromBase64(PhotoFile photoFile, String path, String pathDate) {
    byte[] valueDecoded = Base64.decodeBase64(photoFile.getData());

    String fileName = photoFile.getName();

    createDirectory(path, pathDate);

    String pathLocation = path+pathDate+fileName;

    try (OutputStream stream = new FileOutputStream(pathLocation)) {
      stream.write(valueDecoded);

    } catch (IOException e) {
      LOGGER.info("Failed to write file.", e);
      throw new BusinessLogicException(ResponseCode.CANNOT_WRITE_IMAGE.getCode(), ResponseCode.CANNOT_WRITE_IMAGE.getMessage());
    }

    checkImageFile(pathLocation);

  }

  private static void checkImageFile(String pathLocation) {

    try {
      File file = new File(pathLocation);
      BufferedImage getImage = null;
        getImage = ImageIO.read(file);

      if(isNullReadFile(getImage))
      {
        Path path = Paths.get(pathLocation);
        Files.deleteIfExists(path);
        throw new BusinessLogicException(ResponseCode.UPLOAD_FILE_FAILED.getCode(), ResponseCode.UPLOAD_FILE_FAILED.getMessage());
      }
    } catch (IOException e) {
      LOGGER.info("Failed to read image.", e);
      throw new BusinessLogicException(ResponseCode.CANNOT_READ_IMAGE.getCode(), ResponseCode.CANNOT_READ_IMAGE.getMessage());
    }
  }

  private static boolean isNullReadFile(BufferedImage getImage) {
    return getImage == null;
  }


  public static void createDirectory(String path, String pathDate) {

    String[] pathDateArray = pathDate.split("/");
    Integer countpathDate = pathDateArray.length;

    StringBuilder pathImage = new StringBuilder();
    pathImage.append(path);

    for(Integer x = 0; x < countpathDate; x++)
    {
      pathImage.append(pathDateArray[x] + "/");
      File directory = new File(String.valueOf(pathImage));
      if (! directory.exists()){
        directory.mkdirs();
      }
    }
  }

  public static String generateNamePhoto(String name)
  {
    return (new Random().nextInt(1000) + 1) + new SimpleDateFormat("ddMMyyyy").format(new Date()) + name;
  }

  public static void compressImage(String pathImageLocation, String pathImageDate, String property, String fileName, String fileNameOrigin, Float quality, Boolean statusExecuted) {

    try {
      String nameImageInput = pathImageLocation+pathImageDate+fileName;
      String nameImageOutput = pathImageLocation+pathImageDate+property+fileName;

      if(statusExecuted.equals(true))
      {
        nameImageInput = fileName;
        nameImageOutput = fileName;
      }

      File input = new File(nameImageInput);
      BufferedImage image = null;

        image = ImageIO.read(input);

      OutputStream out = new FileOutputStream(nameImageOutput);

      String[] getExtension = fileNameOrigin.split("\\.");
      Integer countArrayFile = getExtension.length;

      ImageWriter writer =  ImageIO.getImageWritersByFormatName(getExtension[countArrayFile - 1]).next();
      ImageOutputStream ios = ImageIO.createImageOutputStream(out);

      writer.setOutput(ios);

      ImageWriteParam param = writer.getDefaultWriteParam();
      if (param.canWriteCompressed()){

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
      }

      writer.write(null, new IIOImage(image, null, null), param);
    } catch (IOException e) {
      LOGGER.info("cannot read file {}", e);
      throw new BusinessLogicException(ResponseCode.CANNOT_READ_IMAGE.getCode(), ResponseCode.CANNOT_READ_IMAGE.getMessage());
    }


  }

  public static void cropImageCenter(String pathImageLocation, String pathImageDate, String property, String fileName, Integer targetWidth, Integer targetHeight, Boolean statusExecuted) {

    try {
      String nameImageInput = pathImageLocation+pathImageDate+fileName;
      String nameImageOutput = pathImageLocation+pathImageDate+property+fileName;

      Integer defineTargetWidth = targetWidth;
      Integer defineTargetHeight = targetHeight;

      if(statusExecuted.equals(true))
      {
        nameImageInput = fileName;
        nameImageOutput = fileName;
      }

      File files = new File(nameImageInput);
      BufferedImage originalImage = null;

        originalImage = ImageIO.read(files);


      // Get image dimensions
      int height = originalImage.getHeight();
      int width = originalImage.getWidth();

      Integer xCoordinate = (int) Math.floor((double) (width - defineTargetWidth) / 2);
      Integer yCoordinate = (int) Math.floor((double) (height - defineTargetHeight) / 2);

      if(defineTargetWidth > width) {
        defineTargetWidth = width;
        xCoordinate = 0;
      }

      if(defineTargetHeight > height) {
        defineTargetHeight = height;
        yCoordinate = 0;
      }


      // Crop
      BufferedImage croppedImage = originalImage.getSubimage(
          xCoordinate,
          yCoordinate,
          defineTargetWidth,
          defineTargetHeight
      );

      String[] getExtension = fileName.split("\\.");
      Integer countArrayFile = getExtension.length;

      File output = new File(nameImageOutput);
      ImageIO.write(croppedImage, getExtension[countArrayFile - 1], output);
    } catch (IOException e) {
      LOGGER.info("Cannot crop image {}", e);
      throw new BusinessLogicException(ResponseCode.CANNOT_CROP_IMAGE.getCode(), ResponseCode.CANNOT_CROP_IMAGE.getMessage());
    }

  }

  private static Integer getHeightRatio(Integer widthOriginal, Integer heightOriginal, Integer widthRatio)
  {
    Double height = Double.valueOf(heightOriginal);
    return (int) Math.floor( height *  ((double) widthRatio/widthOriginal));
  }


  public static void ratioImageGenerator(String pathImageLocation, String pathImageDate, String property, String fileName, Integer widthRatio, Boolean statusExecuted) {

    try {
      String nameImageInput = pathImageLocation+pathImageDate+fileName;
      String nameImageOutput = pathImageLocation+pathImageDate+property+fileName;

      if(statusExecuted.equals(true))
      {
        nameImageInput = fileName;
        nameImageOutput = fileName;
      }

      File input = new File(nameImageInput);
      BufferedImage imageInput = ImageIO.read(input);

      BufferedImage resized = setImageRatio(imageInput, widthRatio);

      String[] getExtension = fileName.split("\\.");
      Integer countArrayFile = getExtension.length;

      File output = new File(nameImageOutput);

        ImageIO.write(resized, getExtension[countArrayFile - 1], output);
    } catch (IOException e) {
      LOGGER.info("Cannot ratio image {}", e);
      throw new BusinessLogicException(ResponseCode.CANNOT_RATIO_IMAGE.getCode(), ResponseCode.CANNOT_RATIO_IMAGE.getMessage());
    }

  }

  public static Map<String, Map<String, Object>> filePropertyRead(String fileName)
  {
    Map<String, Map<String, Object>> paramProperties = new HashMap<>();
    Map<String, Object> dimension = new HashMap<>();
    Map<String, Object> compression = new HashMap<>();

    try{
      String[] paramConfig = fileName.split(",");

      String wrProperty = "wr";
      String wProperty = "w";
      String hProperty = "h";
      String qProperty = "q";

      for (String getParamConfig : paramConfig) {
        String[] properties = getParamConfig.split("_");
        if (properties[0].equals(wrProperty)) {
          Map<String, Object> wr = new HashMap<>();
          wr.put("value", properties[1]);
          paramProperties.put("wr", wr);
        } else if (properties[0].equals(wProperty)) {
          dimension.put("w", properties[1]);
        } else if (properties[0].equals(hProperty)) {
          dimension.put("h", properties[1]);
        } else if (properties[0].equals(qProperty)) {
          compression.put("q", properties[1]);
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException ai)
    {
      LOGGER.info("Array Index Out Of Bounds Exception ", ai);
      throw new BusinessLogicException(ResponseCode.ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION.getCode(), ResponseCode.ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION.getMessage());
    }


    paramProperties.put("dimension", dimension);
    paramProperties.put("compression", compression);

    return paramProperties;
  }


  public static List<String> generateType(String fileName)
  {
    String[] paramConfig = fileName.split(",");
    List<String> types = new ArrayList<>();

    String typeProperty = "type";

    for (String getParamConfig: paramConfig) {
      String[] properties = getParamConfig.split("_");
      if(properties[0].equals(typeProperty))
      {
        String[] propertiesType = properties[1].split("-");
        for (String getPropertiesType: propertiesType) {
          types.add(getPropertiesType);
        }
      }
    }

    return types;
  }

  private static BufferedImage setImageRatio(BufferedImage img, Integer widthRatio) {

    Integer widthOriginal = img.getWidth();
    Integer heightOriginal = img.getHeight();

    Integer heightRatio = getHeightRatio(widthOriginal, heightOriginal, widthRatio);

    Image thumbnail = img.getScaledInstance(widthRatio, heightRatio, Image.SCALE_SMOOTH);
    BufferedImage bufferedThumbnail = new BufferedImage(widthRatio,
        heightRatio,
        BufferedImage.TYPE_INT_RGB);
    bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);

    return bufferedThumbnail;
  }

  public static void resizeImage(String pathImageLocation, String pathImageDate, String property, String fileName, Integer widthRequest, Integer heightRequest, Boolean statusExecuted) {

    try {
      String nameImageInput = pathImageLocation+pathImageDate+fileName;
      String nameImageOutput = pathImageLocation+pathImageDate+property+fileName;

      if(statusExecuted.equals(true))
      {
        nameImageInput = fileName;
        nameImageOutput = fileName;
      }

      File input = new File(nameImageInput);
      BufferedImage imageInput = null;

      imageInput = ImageIO.read(input);


      BufferedImage resized = setImageResize(imageInput, widthRequest, heightRequest);

      String[] getExtension = fileName.split("\\.");
      Integer countArrayFile = getExtension.length;

      String getNameExtension = getExtension[countArrayFile - 1];

      File output = new File(nameImageOutput);
      ImageIO.write(resized, getNameExtension, output);
    } catch (IOException e) {
      LOGGER.info("Cannot resize image {}", e);
      throw new BusinessLogicException(ResponseCode.CANNOT_RESIZE_IMAGE.getCode(), ResponseCode.CANNOT_RESIZE_IMAGE.getMessage());
    }

  }

  private static BufferedImage setImageResize(BufferedImage img, Integer widthRequest, Integer heightRequest) {

    Image thumbnail = img.getScaledInstance(widthRequest, heightRequest, Image.SCALE_SMOOTH);
    BufferedImage bufferedThumbnail = new BufferedImage(widthRequest,
        heightRequest,
        BufferedImage.TYPE_INT_RGB);
    bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);

    return bufferedThumbnail;
  }

  public static PhotoFile getPhotoDataFromUrl(URL url, String photoName){
    PhotoFile photoFile = new PhotoFile();
    try(
        InputStream inStreamConvert = url.openStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
    ) {
      URLConnection urlConnection = url.openConnection();
      String type = urlConnection.getHeaderField("Content-Type");
      if (isImageType(type)) {
        byte[] chunk = new byte[4096];
        int bytesRead;
        while ((bytesRead = inStreamConvert.read(chunk)) > 0) {
          byteArrayOutputStream.write(chunk, 0, bytesRead);
        }
        byteArrayOutputStream.flush();

        photoFile.setName(photoName.split("\\.")[0]+"."+type.replace("image/",""));
        photoFile.setData(java.util.Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()));
      } else {
        LOGGER.info("Wrong type file ", type);
        throw new BusinessLogicException(ResponseCode.PHOTO_EXTENSION_NOT_MATCH.getCode(),
            ResponseCode.PHOTO_EXTENSION_NOT_MATCH.getMessage());
      }
    }
    catch (Exception e) {
      LOGGER.info("Failed to get data from given url, hence we are uploading image from local assets", e);
      throw new BusinessLogicException(ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getCode(),
          ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getMessage());
    }
    return photoFile;
  }



  private static boolean isImageType(String type){
    return type != null && type.startsWith("image/");
  }

  public static URL stringToUrl(String stringUrl){
    URL url;
    try {
      url = new URL(stringUrl);
    } catch (IOException ioe){
      LOGGER.info("Failed convert string to url ", ioe);
      throw new BusinessLogicException(ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getCode(),
          ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getMessage());
    }

    return url;
  }

}
