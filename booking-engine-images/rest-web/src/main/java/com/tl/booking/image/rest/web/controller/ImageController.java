package com.tl.booking.image.rest.web.controller;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.common.rest.web.model.response.CommonResponse;
import com.tl.booking.image.entity.DateFormatImage;
import com.tl.booking.image.entity.ImagesRequest;
import com.tl.booking.image.entity.constant.ApiPath;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.constant.fields.MandatoryFields;
import com.tl.booking.image.entity.dao.Image;
import com.tl.booking.image.rest.web.model.response.ImagesResponse;
import com.tl.booking.image.service.api.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "Images")
public class ImageController {

  @Autowired
  private ImageService imageService;

  @ApiOperation(value = "Create Images")
  @PostMapping(path = ApiPath.IMAGES)
  public BaseResponse<ImagesResponse> createImages(
      @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
      @Valid @RequestBody ImagesRequest imagesRequest) {

    Image image = this.toImage(imagesRequest);

    Image createdImage = this.imageService.createImage(image, imagesRequest);

    ImagesResponse imageResponse = this.toImageResponse(createdImage);

    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, imageResponse);
  }

  @Async
  @ApiOperation(value = "Create Image From URL")
  @RequestMapping(method = RequestMethod.POST, path = ApiPath.IMAGES_FROM_URL)
  public BaseResponse<ImagesResponse> createImageFromUrl(
      @RequestBody ImagesRequest imagesRequest
  ) {

    Image image = this.toImage(imagesRequest);

    Image createdImage = this.imageService.createImageFromURL(image, imagesRequest);

    ImagesResponse imageResponse = this.toImageResponse(createdImage);

    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
        null, imageResponse);
  }

  @ApiOperation(value = "Get Images")
  @RequestMapping(method = RequestMethod.GET, path = ApiPath.GET_IMAGES + ApiPath.YEAR
      + ApiPath.MONTH + ApiPath.DAY + ApiPath.IMAGE_NAME)
  public ResponseEntity<InputStreamResource> getImages(
      @Valid @NotNull @PathVariable String year,
      @Valid @NotNull @PathVariable String month,
      @Valid @NotNull @PathVariable String day,
      @Valid @NotNull @PathVariable String imageName
  ) {

    DateFormatImage dateFormatImage = this.toDateFormatImage(year, month, day);

    String image = this.imageService.getImage(dateFormatImage, imageName);

    ResponseEntity<InputStreamResource> getOutputStream = this.imageService.outputStream(image);

    this.imageService.removeFileProperty(image);

    return getOutputStream;
  }


  @ApiOperation(value = "Get Images Cloudinary ")
  @RequestMapping(method = RequestMethod.GET, path = ApiPath.GET_IMAGES_CLOUDINARY + ApiPath.YEAR
      + ApiPath.MONTH + ApiPath.DAY + ApiPath.IMAGE_NAME)
  public ResponseEntity<InputStreamResource> getImagesCloudinary(
      @Valid @NotNull @PathVariable String year,
      @Valid @NotNull @PathVariable String month,
      @Valid @NotNull @PathVariable String day,
      @Valid @NotNull @PathVariable String imageName
  ) {

    DateFormatImage dateFormatImage = this.toDateFormatImage(year, month, day);

    String image = this.imageService.getImageCloudinary(dateFormatImage, imageName);

    ResponseEntity<InputStreamResource> getOutputStream = this.imageService
        .outputStreamFromURL(image);

    this.imageService.removeFileProperty(image);

    return getOutputStream;
  }

  @ApiOperation(value = "Reupload Image to Cloudinary ")
  @RequestMapping(method = RequestMethod.GET, path = ApiPath.REUPLOAD_IMAGE )
  public BaseResponse reuploadImage() {

    Boolean result = imageService.reUploadImageToCloudinary();
    return CommonResponse
        .constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, result);
  }

  private DateFormatImage toDateFormatImage(String year, String month, String day) {
    DateFormatImage dateFormatImage = new DateFormatImage();
    dateFormatImage.setYear(year);
    dateFormatImage.setMonth(month);
    dateFormatImage.setDay(day);

    return dateFormatImage;
  }

  private ImagesResponse toImageResponse(Image createdImage) {
    return BeanMapper.map(createdImage, ImagesResponse.class);
  }

  private Image toImage(ImagesRequest imagesRequest) {
    Image image = new Image();

    image.setStoreId((String) MDC.get(MandatoryFields.STORE_ID));
    image.setCreatedBy((String) MDC.get(MandatoryFields.USERNAME));
    image.setUpdatedBy((String) MDC.get(MandatoryFields.USERNAME));
    image.setChannelId((String) MDC.get(MandatoryFields.CHANNEL_ID));
    image.setUsername((String) MDC.get(MandatoryFields.USERNAME));

    image.setName(imagesRequest.getName());

    return image;
  }


  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }

}
