package com.tl.booking.image.service.api;


import com.tl.booking.image.entity.DateFormatImage;
import com.tl.booking.image.entity.ImagesRequest;
import com.tl.booking.image.entity.dao.Image;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ImageService {

  Image createImage(Image image, ImagesRequest imagesRequest);

  String getImage(DateFormatImage dateFormatImage, String imageName);

  String getImageCloudinary(DateFormatImage dateFormatImage, String imageName);

  ResponseEntity<InputStreamResource> outputStream(String imagename);

  ResponseEntity<InputStreamResource> outputStreamFromURL(String imagename);

  void removeFileProperty(String image);

  Image createImageFromURL(Image imageParam, ImagesRequest imagesRequest);

  Boolean reUploadImageToCloudinary();
}
