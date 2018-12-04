package com.tl.booking.promo.code.dao.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.ChannelIdRepositoryCustom;
import com.tl.booking.promo.code.dao.utility.QueryConstructor;
import com.tl.booking.promo.code.entity.FilterParam;
import com.tl.booking.promo.code.entity.constant.enums.CriteriaType;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.fields.BaseMongoFields;
import com.tl.booking.promo.code.entity.constant.fields.ChannelIdFields;
import com.tl.booking.promo.code.entity.dao.ChannelId;
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
public class ChannelIdRepositoryImpl implements ChannelIdRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Page<ChannelId> findChannelIdFilterPaginated(String channelId, String name, Integer page,
      Integer size, String columnSort,
      SortDirection sortDirection) {

    List<FilterParam> filterParams = setFilterParamList(name);

    Pageable pageable = RepositoryImplHelper.setPageable(page, size, sortDirection, columnSort);

    List<ChannelId> channelIds = mongoTemplate.find(QueryConstructor
            .constructBy(channelId, filterParams, pageable, true),
        ChannelId.class);

    List<ChannelId> channelIdsAll = mongoTemplate.find(QueryConstructor
            .constructBy(channelId, filterParams, pageable, false),
        ChannelId.class);

    Page<ChannelId> channelIdPage = new PageImpl<>(channelIds, pageable,
        channelIdsAll.size());

    return channelIdPage;
  }

  @Override
  public Boolean softDeleted(ChannelId channelId, String id) {
    Query query = new Query(where(ChannelIdFields.ID).is(id));
    Update update = new Update();
    update.inc(BaseMongoFields.IS_DELETED, channelId.getIsDeleted());
    update.set(ChannelIdFields.UPDATED_BY, channelId.getUpdatedBy());
    update.set(ChannelIdFields.UPDATED_DATE, channelId.getUpdatedDate());
    WriteResult writeResult = mongoTemplate.updateFirst(query, update, ChannelId.class);

    Boolean result = true;

    if (!writeResult.isUpdateOfExisting()) {
      result = false;
    }

    return result;
  }


  private List<FilterParam> setFilterParamList(String name) {
    List<FilterParam> filterParams = new ArrayList<>();

    FilterParam filterParam = new FilterParam();

    filterParam.setParamName(ChannelIdFields.NAME);
    filterParam.setParamValue(name);
    filterParam.setCriteriaType(CriteriaType.REGEX);
    filterParams.add(filterParam);

    return filterParams;
  }


}
