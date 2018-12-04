package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.promo.code.entity.CalculationResult;
import com.tl.booking.promo.code.entity.CalculationResultBuilder;
import com.tl.booking.promo.code.entity.CostAmount;
import com.tl.booking.promo.code.entity.CostAmountBuilder;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.PaymentDTO;
import com.tl.booking.promo.code.entity.PaymentDTOBuilder;
import com.tl.booking.promo.code.entity.PaymentMethod;
import com.tl.booking.promo.code.entity.PaymentMethodBuilder;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.constant.enums.CalculateType;
import com.tl.booking.promo.code.entity.constant.enums.DataType;
import com.tl.booking.promo.code.entity.constant.enums.InputType;
import com.tl.booking.promo.code.entity.constant.enums.Language;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.CampaignBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustmentBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCodeBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLog;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageLogBuilder;
import com.tl.booking.promo.code.entity.dao.Variable;
import com.tl.booking.promo.code.entity.dao.VariableBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PromoCodeUsageTestVariable {

  public static final String CARD_NUMBER = "1231238899";
  public static String CODE = "CODE123";
  public static String APPLY_PROMOCODE_CODE = "APPLYCODE123";
  public static String CAMPAIGN_ID = "Camp123";
  public static String PROMO_CODE_ID = "promoCode123";

  public static Set<String> USED_PROMOCODES = getUsedPromoCodes();

  public static Campaign CAMPAIGN = new CampaignBuilder().build();
  public static PromoCode PROMO_CODE = new PromoCodeBuilder()
      .withId(PROMO_CODE_ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();


  public static List<OrderDetail> ORDER_DETAILS = getOrderDetails();


  public static Double AMOUNT = 20000.00;
  public static Double DISCOUNT = 0.00;
  public static String PROMO_CODE_JSON = "{"
      + "\"code\": \"" + CODE + "\", "
      + "\"lang\": \"" + Language.ID.getCode().toString() + "\", "

      + "\"orderDetails\": ["
      + "{"
      + "\"amount\": " + AMOUNT + ", "
      + "\"discount\": " + DISCOUNT + ", "
      + "\"orderAttribute\":{\"adult\" : 5}, "
      + "\"referenceId\": " + "\"string\""
      + "}"
      + "], "
      + "\"usedPromoCodes\": [ \"" + CODE + "\"]}";


  public static String APPLY_PROMO_CODE_JSON = "{"
      + "\"code\": \"" + CODE + "\", "
      + "\"orderDetails\": ["
      + "{"
      + "\"amount\": " + AMOUNT + ", "
      + "\"discount\": " + DISCOUNT + ", "
      + "\"orderAttribute\":{\"adult\" : 5}, "
      + "\"referenceId\": " + "\"string\""
      + "}"
      + "], "
      + "\"usedPromoCodes\": [ \"" + CODE + "\"]}";

  public static String UNAPPLY_PROMO_CODE_JSON = CODE;
  public static String STORE_ID = "TIKETCOM";
  public static String REQUEST_ID = "1";
  public static String SERVICE_ID = "1";
  public static String CHANNEL_ID = "web";
  public static String USERNAME = "username";
  public static Integer IS_DELETED = 0;
  public static String CODE_USAGE_LOG = "PROMOCODEAJA";
  public static Double DISCOUNT_AMOUNT = 600000.0;
  public static String ID = "1234abrna";
  public static Integer USED_QTY = 1;
  public static Date DATE_NOW = new Date();
  public static String START_DATE = "2018-01-01";
  public static String END_DATE = "2018-05-01";
  public static Integer PAGE = 1;
  public static Integer SIZE = 5;
  public static String DATE_STRING = "2018-02-10";
  public static Date DATE_TYPE = stringToDate(DATE_STRING);
  public static Date START_DATE_TYPE = stringToDate(START_DATE);
  public static Date END_DATE_TYPE = stringToDate(END_DATE);
  public static String NAME = "BCA Cost";
  public static Double AMOUNT_USAGE_LOG = 160000.00;
  public static Double TOTAL_PRICE = 180000.00;
  public static String ORDER_ATTRIBUTE = "hotelName";
  public static String REF_ID = "ref123";
  public static String BIN_NUMBER = "Bin123";
  public static Integer PAYMENT_ID = 123;


  public static CostAmount PARTNER_COST_AMOUNT = new CostAmountBuilder()
      .withName(NAME)
      .withAmount(AMOUNT)
      .build();
  public static CostAmount COMPANY_COST_AMOUNT = new CostAmountBuilder()
      .withName(NAME)
      .withAmount(AMOUNT)
      .build();
  public static List<CostAmount> PARTNER_COST_AMOUNTS = Arrays.asList(PARTNER_COST_AMOUNT);
  public static List<CostAmount> COMPANY_COST_AMOUNTS = Arrays.asList(COMPANY_COST_AMOUNT);
  public static PromoCodeUsageLog PROMO_CODE_USAGE_LOG_LIST_REQUEST = new PromoCodeUsageLogBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)
      .withCode(CODE)
      .withDate(stringToDate(START_DATE))
      .withDate(stringToDate(END_DATE))
      .build();
  public static PromoCodeUsageLog PROMO_CODE_USAGE_LOG = new PromoCodeUsageLogBuilder()
      .withId(ID)
      .withCode(CODE_USAGE_LOG)
      .withDate(DATE_TYPE)
      .withDiscountAmount(DISCOUNT_AMOUNT)
      .withPartnerCostAmount(PARTNER_COST_AMOUNTS)
      .withCompanyCostAmount(COMPANY_COST_AMOUNTS)
      .withUsedQty(USED_QTY)
      .build();
  public static List<PromoCodeUsageLog> PROMO_CODE_USAGE_LOG_LIST = Arrays
      .asList(PROMO_CODE_USAGE_LOG_LIST_REQUEST, PROMO_CODE_USAGE_LOG);
  public static Page<PromoCodeUsageLog> PROMO_CODE_USAGE_LOG_LIST_PAGE = new PageImpl<>(
      PROMO_CODE_USAGE_LOG_LIST);
  public static MandatoryRequest MANDATORY_REQUEST = new MandatoryRequestBuilder()
      .withStoreId(STORE_ID).withChannelId(CHANNEL_ID).withRequestId(REQUEST_ID)
      .withServiceId(SERVICE_ID).withUsername(USERNAME).build();
  public static PaymentDTO PAYMENT_DTO = new PaymentDTOBuilder()
      .withPaymentId(PAYMENT_ID)
      .withBinNumber(BIN_NUMBER)
      .withCardNumber(CARD_NUMBER)
      .build();
  public static PaymentMethod PAYMENT_METHOD = new PaymentMethodBuilder()
      .withPaymentId(PAYMENT_ID)
      .withBinNumbers(binNumbers())
      .build();
  public static List<PaymentMethod> PAYMENT_METHODS = Arrays.asList(PAYMENT_METHOD);
  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT = new PromoCodeAdjustmentBuilder()

      .withCalculateType(CalculateType.ORDER_DETAIL)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCode(CODE)
      .withValidAllOrderDetails(true)
      .withPaymentMethods(PAYMENT_METHODS)
      .build();
  public static PromoCodeObject PROMO_CODE_OBJECT = getPromoCodeObject();
  public static CalculationResult CALC_RESULT = new CalculationResultBuilder()
      .withOrderDetails(ORDER_DETAILS)
      .withPromoCodeObject(PROMO_CODE_OBJECT)
      .withTotalDiscount(DISCOUNT)
      .withTotalPrice(TOTAL_PRICE)
      .withUsedPromoCodes(USED_PROMOCODES)
      .build();
  public static String REF_ORDER_ID = "ORDER1";

  public static final String PRODUCT_TYPE = "flight";
  public static String CALC_PROMO_CODE = "{\n"
      + "  \"code\": \"" + CODE + "\",\n"
      + "  \"lang\": \"" + Language.ID.getCode() + "\",\n"
      + "  \"orderDetails\": [\n"
      + "    {\n"
      + "      \"amount\": " + AMOUNT + ",\n"
      + "      \"discount\": " + DISCOUNT + ",\n"
      + "      \"orderAttribute\": {\"adult\" : \"5\"},\n"
      + "      \"referenceId\": \"" + REF_ID + "\",\n"
      + "      \"productType\": \"" + PRODUCT_TYPE + "\"\n"
      + "    }\n"
      + "  ],\n"
      + "  \"payment\": {\n"
      + "    \"binNumber\": \"" + BIN_NUMBER + "\",\n"
      + "    \"cardNumber\": \"" + CARD_NUMBER + "\",\n"
      + "    \"paymentId\": \" " + PAYMENT_ID + " \" \n"
      + "  },\n"
      + "  \"totalPrice\": " + TOTAL_PRICE + ",\n"
      + "  \"usedPromoCodes\": [\n"
      + "    \"" + CODE + "\"\n"
      + "  ],\n"
      + "      \"referenceId\": \"" + REF_ORDER_ID + "\"\n"
      + "}";
  public static String CALC_PROMO_CODE_WITHOUT_PAYMENT = "{\n"
      + "  \"code\": \"" + CODE + "\",\n"
      + "  \"lang\": \"" + Language.ID.getCode() + "\",\n"
      + "  \"orderDetails\": [\n"
      + "    {\n"
      + "      \"amount\": " + AMOUNT + ",\n"
      + "      \"discount\": " + DISCOUNT + ",\n"
      + "      \"orderAttribute\": {\"adult\" : \"5\"},\n"
      + "      \"referenceId\": \"" + REF_ID + "\",\n"
      + "      \"productType\": \"" + PRODUCT_TYPE + "\"\n"
      + "    }\n"
      + "  ],\n"
      + "  \"payment\": {\n"
      + "    \"binNumber\": \"" + BIN_NUMBER + "\",\n"
      + "    \"cardNumber\": \"" + CARD_NUMBER + "\",\n"
      + "    \"paymentId\": \" " + PAYMENT_ID + " \" \n"
      + "  },\n"
      + "  \"totalPrice\": " + TOTAL_PRICE + ",\n"
      + "  \"usedPromoCodes\": [\n"
      + "    \"" + CODE + "\"\n"
      + "  ],\n"
      + "      \"referenceId\": \"" + REF_ORDER_ID + "\"\n"
      + "}";
  public static String CHECK_PAYMENT_DISCOUNT = "{"
      + "  \"code\": \"" + CODE + "\"\n"
      + "}";
  public static String UNAPPLY_CODE = "{\n"
      + "  \"cardNumber\": \"" + CARD_NUMBER + "\",\n"
      + "  \"code\": \"" + CODE + "\",\n"
      + "  \"referenceId\": \"123123123\"\n"
      + "}";

  private static final List<Variable> getVariables() {
    Variable variable = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "<>", "<=", ">="))
        .withDataType(DataType.STRING)
        .withParam("airline_name")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    return Arrays.asList(
        variable
    );
  }

  private static final Map<String, Variable> getMapVariables() {
    List<Variable> variables = getVariables();

    Map<String, Variable> variableMap = new HashMap<>();
    for (Variable variable : variables) {
      variableMap.put(variable.getParam(), variable);
    }
    return variableMap;
  }

  private static <T> List<T> mergeList(Object... objects) {
    List<T> list = new ArrayList<>();

    for (Object object : objects) {
      list.add((T) object);
    }

    return list;
  }

  private static final Set<String> getUsedPromoCodes() {
    Set<String> usedPromoCodes = new HashSet<>();
    usedPromoCodes.add(CODE);
    return usedPromoCodes;
  }

  private static List<OrderDetail> getOrderDetails() {
    OrderDetail orderDetail = getOrderDetail();
    return Arrays.asList(orderDetail);
  }

  private static OrderDetail getOrderDetail() {
    OrderDetail orderDetail = new OrderDetail();

    Map<String, String> orderAttributes = new HashMap<>();
    orderAttributes.put("adult", "5");

    orderDetail.setOrderAttribute(orderAttributes);
    orderDetail.setReferenceId("ref123");
    orderDetail.setAmount(20000.00);
    orderDetail.setDiscount(0.00);

    return orderDetail;
  }

  static Date stringToDate(String date) {
    String pattern = "";
    if (date.length() == 19) {
      pattern = "yyyy-MM-dd HH:mm:ss";

      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(pattern, Locale.ENGLISH);

      LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

      return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

    } else if (date.length() == 10) {
      pattern = "yyyy-MM-dd";

      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(pattern, Locale.ENGLISH);

      LocalDate localDate = LocalDate.parse(date, formatter);

      return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    return null;
  }

  public static Set<String> binNumbers() {
    Set<String> binNumbers = new HashSet<>();
    binNumbers.add(BIN_NUMBER);

    return binNumbers;
  }

  private static final PromoCodeObject getPromoCodeObject() {
    PromoCodeObject promoCodeObject = new PromoCodeObject();
    promoCodeObject.setVariableMap(getMapVariables());
    promoCodeObject.setPromoCodeAdjustment(PROMO_CODE_ADJUSTMENT);
    promoCodeObject.setCampaign(CAMPAIGN);
    promoCodeObject.setPromoCode(PROMO_CODE);
    return promoCodeObject;
  }

}
