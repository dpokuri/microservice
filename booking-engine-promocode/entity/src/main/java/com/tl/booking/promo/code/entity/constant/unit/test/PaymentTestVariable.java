package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.dao.Payment;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PaymentTestVariable {

  public static String NAME = "Payment Asqo";
  public static String PAYMENT_ID = "PaymentIdMonolith";
  public static Boolean USE_BIN_NUMBER = true;
  public static String NAME_2 = "Payment Asqo 2";
  public static String ID = "null";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static String CACHE_KEY = CacheKey.PAYMENT + "-" + CommonTestVariable.STORE_ID + "-" + ID;

  public static String CACHE_KEY_FIND_ALL = CacheKey.PAYMENT + "-" + CommonTestVariable.STORE_ID;
  public static Payment PAYMENT = generatePayment();
  public static Payment PAYMENT_2 = generatePayment2();
  public static Payment PAYMENT_REQUEST = paymentRequest();
  public static Payment PAYMENT_FIND_IS_DELETED = paymentIsDeleted();
  public static List<Payment> PAYMENT_LIST = Arrays.asList(PAYMENT, PAYMENT);
  public static List<Payment> PAYMENT_LIST_IS_DELETED = Arrays.asList(PAYMENT_FIND_IS_DELETED);
  public static List<Payment> PAYMENT_NULL = Arrays.asList();
  public static Page<Payment> PAYMENT_PAGE = new PageImpl<>(PAYMENT_LIST);
  public static Page<Payment> PAYMENT_PAGE_NULL = new PageImpl<>(PAYMENT_NULL);
  public static String PAYMENT_JSON = "{\n"
      + "  \"name\": \"" + NAME + "\",\n"
      + "  \"paymentId\": \"" + PAYMENT_ID + "\",\n"
      + "  \"useBinNumber\": \"" + USE_BIN_NUMBER + "\"\n"
      + "}";

  private static Payment generatePayment() {
    Payment payment = new Payment();
    payment.setPaymentId(PAYMENT_ID);
    payment.setName(NAME);
    payment.setUseBinNumber(USE_BIN_NUMBER);
    payment.setStoreId(CommonTestVariable.STORE_ID);
    payment.setChannelId(CommonTestVariable.CHANNEL_ID);
    payment.setUpdatedBy(CommonTestVariable.USERNAME);
    payment.setCreatedBy(CommonTestVariable.USERNAME);
    payment.setUsername(CommonTestVariable.USERNAME);
    payment.setCreatedDate(new Date());
    payment.setUpdatedDate(new Date());

    return payment;
  }

  private static Payment generatePayment2() {
    Payment payment = new Payment();
    payment.setPaymentId(PAYMENT_ID);
    payment.setName(NAME_2);
    payment.setUseBinNumber(USE_BIN_NUMBER);
    payment.setStoreId(CommonTestVariable.STORE_ID);
    payment.setChannelId(CommonTestVariable.CHANNEL_ID);
    payment.setUpdatedBy(CommonTestVariable.USERNAME);
    payment.setCreatedBy(CommonTestVariable.USERNAME);
    payment.setUsername(CommonTestVariable.USERNAME);
    payment.setCreatedDate(new Date());
    payment.setUpdatedDate(new Date());

    return payment;
  }

  private static Payment paymentRequest() {
    Payment payment = new Payment();
    payment.setPaymentId(PAYMENT_ID);
    payment.setName(NAME);
    payment.setUseBinNumber(USE_BIN_NUMBER);
    payment.setStoreId(CommonTestVariable.STORE_ID);
    payment.setChannelId(CommonTestVariable.CHANNEL_ID);
    payment.setUpdatedBy(CommonTestVariable.USERNAME);
    payment.setCreatedBy(CommonTestVariable.USERNAME);
    payment.setUsername(CommonTestVariable.USERNAME);

    return payment;
  }

  private static Payment paymentIsDeleted() {
    Payment payment = new Payment();
    payment.setPaymentId(PAYMENT_ID);
    payment.setName(NAME);
    payment.setUseBinNumber(USE_BIN_NUMBER);

    return payment;
  }
}
