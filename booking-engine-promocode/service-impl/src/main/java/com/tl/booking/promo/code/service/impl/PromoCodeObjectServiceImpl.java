package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.CampaignRepository;
import com.tl.booking.promo.code.dao.api.PromoCodeAdjustmentRepository;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.PromoCodeObjectBuilder;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CampaignService;
import com.tl.booking.promo.code.service.api.PromoCodeAdjustmentService;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.PromoCodeService;
import com.tl.booking.promo.code.service.api.VariableService;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


@Service
public class PromoCodeObjectServiceImpl implements PromoCodeObjectService {

  @Autowired
  PromoCodeAdjustmentRepository promoCodeAdjustmentRepository;

  @Autowired
  CampaignRepository campaignRepository;

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  private PromoCodeService promoCodeService;

  @Autowired
  private CampaignService campaignService;

  @Autowired
  private PromoCodeAdjustmentService promoCodeAdjustmentService;

  @Autowired
  private VariableService variableService;

  @Override
  public Single<PromoCodeObject> findPromoCodeObjectByStoreIdAndCode(
      MandatoryRequest mandatoryRequest,
      String code) {
    return this.promoCodeService.findPromoCodeByCodeAndStatus(mandatoryRequest, code,
        PromoCodeStatus.ACTIVE)
        .map(promoCode ->
            new PromoCodeObjectBuilder().withPromoCode(promoCode).build())
        .flatMap(promoCodeObjectWithPromoCode ->
            this.campaignService.findCampaignById(mandatoryRequest,
                promoCodeObjectWithPromoCode.getPromoCode().getCampaignId()).map(campaign -> {
                  if(!isActiveCampaign(campaign)){
                    throw new BusinessLogicException(ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE
                        .getCode(), ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getMessage());
                  }
              promoCodeObjectWithPromoCode.setCampaign(campaign);
              return promoCodeObjectWithPromoCode;
            })
        )
        .flatMap(promoCodeObjectWithPromoCodeWithCampaign ->
            this.promoCodeAdjustmentService.findPromoCodeAdjustmentById(mandatoryRequest,
                promoCodeObjectWithPromoCodeWithCampaign.getCampaign().getPromoCodeAdjustmentId())
                .map(promoCodeAdjustment -> {

                  if(!isActivePromoCodeAdjustment(promoCodeAdjustment)){
                    throw new BusinessLogicException(ResponseCode.ADJUSTMENT_NOT_ACTIVE
                        .getCode(), ResponseCode.ADJUSTMENT_NOT_ACTIVE.getMessage());
                  }

                  promoCodeObjectWithPromoCodeWithCampaign
                      .setPromoCodeAdjustment(promoCodeAdjustment);
                  return promoCodeObjectWithPromoCodeWithCampaign;
                })
        )
        .flatMap(promoCodeObjectWithPromoCodeWithCampaignWithAdjustment ->
            this.variableService.findVariablesMapped(mandatoryRequest).map(mappedVariables -> {
              promoCodeObjectWithPromoCodeWithCampaignWithAdjustment
                  .setVariableMap(mappedVariables);
              return promoCodeObjectWithPromoCodeWithCampaignWithAdjustment;
            })
        );
  }

  private boolean isActivePromoCodeAdjustment(PromoCodeAdjustment promoCodeAdjustment) {
    return PromoCodeAdjustmentStatus.ACTIVE.equals(promoCodeAdjustment.getPromoCodeAdjustmentStatus());
  }

  private boolean isActiveCampaign(Campaign campaign) {
    return CampaignStatus.ACTIVE.equals(campaign.getCampaignStatus());
  }

  @Override
  public Single<PromoCodeObject> findPromoCodeObjectByCampaignIdMergeWithPromoCode
      (MandatoryRequest mandatoryRequest, String campaignId,
          PromoCode promoCode) {
    return this.campaignService.findCampaignById(mandatoryRequest, campaignId)
        .map(campaign -> {
          PromoCodeObject promoCodeObject = new PromoCodeObject();
          promoCodeObject.setCampaign(campaign);
          promoCodeObject.setPromoCode(promoCode);

          return promoCodeObject;
        })
        .flatMap(promoCodeObject -> this.promoCodeAdjustmentService
              .findPromoCodeAdjustmentById
              (mandatoryRequest, promoCodeObject.getCampaign().getPromoCodeAdjustmentId())
            .map
                (promoCodeAdjustment
                -> {
                      promoCodeObject.setPromoCodeAdjustment(promoCodeAdjustment);

                      return promoCodeObject;
                    })
        );
  }
}
