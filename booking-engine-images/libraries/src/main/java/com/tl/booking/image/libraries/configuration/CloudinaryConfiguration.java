package com.tl.booking.image.libraries.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tl.booking.image.entity.configuration.CloudinaryProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {

  @Autowired
  CloudinaryProperties cloudinaryProperties;

  @Bean
  public Cloudinary createCloudinary() {

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
        "overwrite", cloudinaryProperties.getOverwrite(),
        "cloud_name", cloudinaryProperties.getCloud_name(),
        "api_key", cloudinaryProperties.getApi_key(),
        "api_secret", cloudinaryProperties.getApi_secret(),
        "resource_type", cloudinaryProperties.getResource_type(),
        "private_cdn",cloudinaryProperties.getPrivate_cdn())
    );

    return cloudinary;
  }

}
