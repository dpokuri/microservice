package com.tl.booking.image.service.impl;

import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.tl.booking.image.entity.ImagesRequest;
import com.tl.booking.image.entity.constant.enums.CloudinaryParameter;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.libraries.configuration.CloudinaryConfiguration;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.service.api.CloudinaryService;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

  @Autowired
  private CloudinaryConfiguration cloudinaryConfiguration;

  @Override
  public String uploadImage(String pathDate,ImagesRequest imagesRequest)  {
    String[] imagesName = imagesRequest.getName().split("\\.");

    if (imagesName.length > 0) {
      imagesRequest.setName(imagesName[0]);
    }

    Map paramUpload = ObjectUtils
        .asMap("public_id", pathDate+imagesRequest.getName(),
            "use_filename", true,
            "folder",imagesRequest.getFolder(),
            "transformation", setTransformation(imagesRequest));

    try {
      Map uploadResult = cloudinaryConfiguration.createCloudinary().uploader()
          .upload("data:image/png;base64," + imagesRequest.getData(), paramUpload);
      String url = (String) uploadResult.get("secure_url");
      return url;
    } catch (IOException ex) {
      throw new BusinessLogicException(ResponseCode.UPLOAD_FILE_FAILED.getCode(),
          ResponseCode.UPLOAD_FILE_FAILED.getMessage());
    }
  }

  private Transformation setTransformation(ImagesRequest imagesRequest){

    Transformation transformation = new Transformation();
    if(!Objects.isNull(imagesRequest.getCrop())){
      transformation.crop(imagesRequest.getCrop());
    }
    if(!Objects.isNull(imagesRequest.getWidth())){
      transformation.width(imagesRequest.getWidth());
    }
    if(!Objects.isNull(imagesRequest.getHeight())){
      transformation.height(imagesRequest.getHeight());
    }
    transformation.quality("auto");
    return transformation;
  }

  @Override
  public String uploadImageFromFile(File file,String imageName) {
    String[] imagesName = imageName.split("\\.");

    if (imagesName.length > 0) {
      imageName = imagesName[0];
    }
    Map paramUpload = ObjectUtils
        .asMap("public_id", imageName, "use_filename", true);

    try {
      Map uploadResult = cloudinaryConfiguration.createCloudinary().uploader()
          .upload(file, paramUpload);
      String url = (String) uploadResult.get("url");
      return url;
    } catch (IOException ex) {
      throw new BusinessLogicException(ResponseCode.UPLOAD_FILE_FAILED.getCode(),
          ResponseCode.UPLOAD_FILE_FAILED.getMessage());
    }
  }

  @Override
  public String getImagesOriginal(String imageName) {
    return cloudinaryConfiguration.createCloudinary().url().transformation(new Transformation()).generate(imageName);
  }

  @Override
  public String imageTransformationRatio(String widthRatio, String imageName) {
    return cloudinaryConfiguration.createCloudinary().url().transformation(new Transformation().width(widthRatio)
        .crop(CloudinaryParameter.CROP_SCALE.getValue())
        .quality(CloudinaryParameter.QUALITY_AUTO.getValue())).generate(imageName);
  }

  @Override
  public String imageTransformationCropCenter(String width, String height, String imageName) {
    return cloudinaryConfiguration.createCloudinary().url().transformation(new Transformation().width(width).height(height)
        .crop(CloudinaryParameter.CROP_THUMB.getValue())
        .quality(CloudinaryParameter.QUALITY_AUTO.getValue())).generate(imageName);
  }

  @Override
  public String imageTransformationResize(String width, String height, String imageName) {
    return cloudinaryConfiguration.createCloudinary().url().transformation(new Transformation().width(width).height(height)
        .quality(CloudinaryParameter.QUALITY_AUTO.getValue())).generate(imageName);
  }
}
