package com.tl.booking.image.service.api;

import com.tl.booking.image.entity.PhotoFile;

public interface UploadService {
  void createdPhoto(PhotoFile photoFile, String pathImageLocation, String pathDate);

  void generateImage(String dateFormatImage, String property, String name);
}
