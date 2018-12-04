package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.CampaignRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.CampaignFields;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.libraries.utility.RepositoryImplHelper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class CampaignRepositoryImpl implements CampaignRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Boolean softDeleted(Campaign campaign, String id) {
    Query query = new Query(where(CampaignFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, campaign.getIsDeleted());
    update.set(CampaignFields.UPDATED_BY, campaign.getUpdatedBy());
    update.set(CampaignFields.UPDATED_DATE, campaign.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, Campaign.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }

  @Override
  public Boolean updateStatus(Campaign campaign) {
    Query query = new Query(where(CampaignFields.ID).is(campaign.getId()));
    Update update = new Update();
    update.set(CampaignFields.CAMPAIGN_STATUS, campaign.getCampaignStatus());
    update.set(CampaignFields.UPDATED_BY, campaign.getUpdatedBy());
    update.set(CampaignFields.UPDATED_DATE, campaign.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, Campaign.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }

  @Override
  public Page<Campaign> findCampaignFilterPaginated(String storeId, String name,
      String campaignStatus, Integer page, Integer size, String columnSort,
      SortDirection sortDirection) {

    List<FilterParam> filterParams = this.setFilterParamList(name, campaignStatus);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<Campaign> campaigns = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, true),
        Campaign.class);

    List<Campaign> campaignsAll = mongoTemplate.find(QueryConstructor
            .constructBy(storeId, filterParams, pageable, false),
        Campaign.class);

    Page<Campaign> campaignPage = new PageImpl<>(campaigns, pageable, campaignsAll.size());

    return campaignPage;
  }

  private List<FilterParam> setFilterParamList(String name, String campaignStatus) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(CampaignFields.NAME);
    filterParam.setParamValue(name);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    filterParam = new FilterParam();
    filterParam.setParamName(CampaignFields.CAMPAIGN_STATUS);
    filterParam.setParamValue(campaignStatus);
    filterParam.setCriteriaType(CriteriaType.EXACT);
    filterParams.add(filterParam);

    return filterParams;
  }

}
