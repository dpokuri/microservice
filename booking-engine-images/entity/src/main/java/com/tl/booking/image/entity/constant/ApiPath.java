package com.tl.booking.image.entity.constant;

public interface ApiPath {
    /* Change base path to micro service name ex : payment, promotion, member, login, etc
        String BASE_PATH = {project_name};
        String SYSTEM_PARAMETER = BASE_PATH + "/systemParameter";
    */

  String BASE_PATH = "/tix-image";
  String SYSTEM_PARAMETER = BASE_PATH + "/systemParameters";
  String ID = "/{id}";
  String IMAGES = BASE_PATH + "/images";
  String GET_IMAGES = "/images";
  String GET_IMAGES_CLOUDINARY = "/imagesCloudinary";
  String IMAGES_FROM_URL = BASE_PATH + "/imagesFromUrl";
  String REUPLOAD_IMAGE = BASE_PATH + "/reuploadImage";

  String IMAGE_NAME = "/{imageName:.+}";
  String YEAR = "/{year}";
  String MONTH = "/{month}";
  String DAY = "/{day}";
}
