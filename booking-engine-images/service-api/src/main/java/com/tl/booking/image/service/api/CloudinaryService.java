package com.tl.booking.image.service.api;

import com.tl.booking.image.entity.ImagesRequest;
import java.io.File;

public interface CloudinaryService {

  String uploadImage(String pathDate,ImagesRequest imagesRequest);

  String uploadImageFromFile( File file,String imageName);

  String getImagesOriginal(String imageName);

  String imageTransformationRatio(String widthRatio, String imageName);

  String imageTransformationCropCenter(String width, String height,
      String imageName);

  String imageTransformationResize(String width, String height, String imageName);

}
