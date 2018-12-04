package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.CardType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardTypeRepository extends MongoRepository<CardType, String>,
    CardTypeRepositoryCustom {

  CardType save(CardType cardType);

  CardType findCardTypeByStoreIdAndNameAndBankIdAndIsDeleted(String storeId, String name, String bankId, Integer isDeleted);

  CardType findCardTypeByStoreIdAndIdAndIsDeleted(String storeId, String id, Integer isDeleted);
}
