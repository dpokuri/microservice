package com.tl.booking.promo.code.entity.constant.enums;

public enum ResponseCode {
  SUCCESS("SUCCESS", "Sukses", "Success"),
  SYSTEM_ERROR("SYSTEM_ERROR", "Maaf, sistem kode promo dalam perbaikan."
      + " Coba lagi nanti", "Sorry, promo code system is under maintenance."
      + " Try again later."),
  RUNTIME_ERROR("RUNTIME_ERROR", "Maaf, sistem kode promo dalam perbaikan."
      + " Coba lagi nanti", "Sorry, promo code system is under maintenance."
      + " Try again later."),
  BIND_ERROR("BIND_ERROR", "Mohon isi parameter yang "
      + "diwajibkan", "Please fill in mandatory parameter"),
  METHOD_ARGUMENTS_NOT_VALID("METHOD_ARGUMENTS_NOT_VALID",
      "Terdapat parameter yang tidak valid", "There are invalid parameters"),

  DUPLICATE_DATA("DUPLICATE_DATA", "Data duplikat", "Duplicate data"),
  DUPLICATE_DATA_BY_CODE("DUPLICATE_DATA_BY_CODE", "Terdapat data "
      + "duplikat", "Duplicate data by Code"),
  DATA_NOT_EXIST("DATA_NOT_EXIST", "Tidak ada data", "No data exist"),

  PROMO_CODE_NOT_AVAILABLE("PROMO_CODE_NOT_AVAILABLE", "Kode Promo "
      + "tidak tersedia", "Promo Code not available"),
  PROMO_CODE_NEVER_USED("PROMO_CODE_NEVER_USED", "Kode Promo tidak "
      + "pernah digunakan", "Promo Code never used"),
  CANNOT_COMBINE("CANNOT_COMBINE", "Kode Promo ini tidak dapat "
      + "digabungkan", "Promo Code cannot be combined"),
  USED_PROMO_CODE_CANNOT_COMBINE("USED_PROMO_CODE_CANNOT_COMBINE", "Kode Promo yang telah digunakan tidak dapat digabungkan", "Used Promo Code cannot be "
      + "combined"),
  PROMO_CODE_NOT_VALID_NO_DISCOUNT("PROMO_CODE_NOT_VALID_NO_DISCOUNT", "Kode Promo tidak valid, tidak ada diskon",
      "Promo code not valid, no discount returned"),
  INVALID_STORE_ID("INVALID_STORE_ID", "Merchant tidak valid", "Store ID not valid"),
  INVALID_PAYMENT_METHOD("INVALID_PAYMENT_METHOD", "Metode pembayaran "
      + "tidak valid", "Invalid Payment Method"),
  INVALID_CHANNEL_ID("INVALID_CHANNEL_ID", "Channel tidak valid", "Channel ID not valid"),
  NOT_VALID_USAGE_RULE("NOT_VALID_USAGE_RULE", "Ketentuan Penggunaan "
      + "tidak "
      + "valid", "Usage Rule not valid"),
  NO_CAMPAIGN_EXISTED("NO_CAMPAIGN_EXISTED", "Tidak ada campaign", "No Campaign existed"),
  INVALID_PROMO_CODE_RULE("INVALID_PROMO_CODE_RULE", "Syarat dan "
      + "Ketentuan Kode Promo tidak valid", "Invalid Promo Code Rule"),
  PROMO_CODE_EXIST("PROMO_CODE_EXIST", "Kode "
      + "Promo terdapat pada campaign lain yang masih aktif", "There is active campaign with same promo code"),
  PROMO_CODE_NOT_EXIST("PROMO_CODE_NOT_EXIST", "Kode Promo tidak ada", "Promo code not exist"),
  USAGE_COUNT_CANNOT_SET_ZERO("USAGE_COUNT_CANNOT_SET_ZERO",
      "Ketentuan Penggunaan tidak diset 0", "Usage Count Cannot Set Zero"),
  USAGE_RULES_MUST_BE_NOT_EMPTY("USAGE_RULES_MUST_BE_NOT_EMPTY",
      "Ketentuan Penggunaan tidak boleh kosong", "Usage Rules Must Be Not Empty"),

  PROMO_CODE_STATUS_NOT_ACTIVE("PROMO_CODE_STATUS_NOT_ACTIVE",
      "Kode Promo tidak aktif", "Promo Code Status is Not Active"),
  PROMO_CODE_STATUS_NOT_IN_ACTIVE("PROMO_CODE_STATUS_NOT_IN_ACTIVE", "Kode Promo masih aktif",
      "Promo Code Status is Not InActive"),

  RULE_NOT_VALID("RULE_NOT_VALID", "Rule not valid", "Syarat dan Ketentuan tidak valid"),
  CAMPAIGN_NOT_EXIST("CAMPAIGN_NOT_EXIST", "Please enter valid campaign", "Campaign Tidak Ada"),
  ADJUSTMENT_NOT_EXIST("ADJUSTMENT_NOT_EXIST", "Please enter valid adjustment", "Adjustment tidak"
      + " valid"),
  ADJUSTMENT_NOT_ACTIVE("ADJUSTMENT_NOT_ACTIVE",
      "Adjustment not active yet, please activate your adjustment", "Adjustment tidak aktif, "
      + "mohon aktifkan adjustment ini"),
  VARIABLE_NOT_EXIST("VARIABLE_NOT_EXIST", "Variable not exist", "Variable tidak ada"),
  BANK_NOT_EXIST("BANK_NOT_EXIST", "Bank not exist", "Bank tidak ada"),
  BIN_NUMBER_NOT_EXIST("BIN_NUMBER_NOT_EXIST", "BinNumber not exist", "BinNumber tidak ada"),
  PAYMENT_NOT_EXIST("PAYMENT_NOT_EXIST", "Payment not exist", "Payment tidak ada"),
  CARD_TYPE_NOT_EXIST("CARD_TYPE_NOT_EXIST", "CardType not exist", "CardType tidak ada"),
  PRODUCT_TYPE_NOT_EXIST("PRODUCT_TYPE_NOT_EXIST", "ProductType not exist", "ProductType tidak "
      + "ada"),
  CODE_EXIST_OTHER_RECORD("CODE_EXIST_OTHER_RECORD", "Code existed in other record", "Kode Promo "
      + "telah ada pada record lain"),
  PARAM_EXIST_OTHER_RECORD("PARAM_EXIST_OTHER_RECORD", "Param existed in other record", "Param "
      + "telah ada pada record lain"),
  NAME_EXIST_OTHER_RECORD("NAME_EXIST_OTHER_RECORD", "Name existed in other record", "Nama Promo "
      + "telah ada pada record lain"),
  ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST("ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST",
      "Promo Code Adjustment Status Active not exist", "Tidak ada Kode Promo berstatus aktif"),
  PROMO_CODE_ADJUSTMENT_STATUS_NOT_DRAFT("PROMO_CODE_ADJUSTMENT_STATUS_NOT_DRAFT",
      "Promo Code Adjustment Status is Not Draft", "Adjustment Kode Promo tidak dalam status "
      + "'DRAFT'"),
  PROMO_CODE_ADJUSTMENT_STATUS_NOT_IN_ACTIVE("PROMO_CODE_ADJUSTMENT_STATUS_NOT_IN_ACTIVE",
      "Promo Code Adjustment Status is Not InActive", "Adjustment Kode Promo tidak dalam status "
      + "'INACTIVE'"),
  PROMO_CODE_ADJUSTMENT_STATUS_CANNOT_UPDATE("PROMO_CODE_ADJUSTMENT_STATUS_CANNOT_UPDATE",
      "Promo Code Adjustment Status cannot Update", "Adjustment Kode Promo tidak dapat diubah"),
  CAMPAIGN_STATUS_NOT_DRAFT("CAMPAIGN_STATUS_NOT_DRAFT", "Campaign Status is Not Draft",
      "Campaign tidak dalam status 'DRAFT'"),
  CAMPAIGN_STATUS_NOT_PENDING("CAMPAIGN_STATUS_NOT_PENDING", "Campaign Status is Not Pending",
      "Campaign tidak dalam status 'PENDING'"),
  CAMPAIGN_STATUS_NOT_ACTIVE("CAMPAIGN_STATUS_NOT_ACTIVE", "Campaign Status is Not Active",
      "Campaign Status tidak Aktif"),
  CANNOT_UPDATE_CAUSE_CAMPAIGN_STATUS_ACTIVE("CANNOT_UPDATE_CAUSE_CAMPAIGN_STATUS_ACTIVE", "Cannot Update Cause Campaign Status Is Active",
      "Tidak dapat update karena campaign status sedang aktif"),
  CANNOT_UPDATE_CAUSE_PROMO_CODE_STATUS_ACTIVE("CANNOT_UPDATE_CAUSE_PROMO_CODE_STATUS_ACTIVE", "Cannot Update Cause PromoCode Status Is Active",
      "Tidak dapat update karena PromoCode status sedang aktif"),
  CANNOT_DELETE_CAUSE_PROMO_CODE_IS_EXIST("CANNOT_DELETE_CAUSE_PROMO_CODE_IS_EXIST", "Cannot Delete Cause PromoCode Is Exist",
      "Tidak dapat delete karena PromoCode eksis"),
  CANNOT_DELETE_CAUSE_CAMPAIGN_IS_EXIST("CANNOT_DELETE_CAUSE_CAMPAIGN_IS_EXIST", "Cannot Delete Cause Campaign Is Exist",
      "Tidak dapat delete karena Campaign eksis"),
  PROMO_CODE_NOT_ACTIVE("PROMO_CODE_NOT_ACTIVE", "Promo Code not Active", "Promo Code tidak Aktif"),
  PROMO_CODE_INVALID_CHARACTER("PROMO_CODE_INVALID_CHARACTER", "Promo Code Can Not Be Filled Space", "Promo Code Tidak Boleh Diisi Spasi"),
  DATE_NOT_VALID("DATE_NOT_VALID", "Date not valid", "Format Tanggal Tidak Valid"),
  INVALID_BIN_NUMBER("INVALID_BIN_NUMBER", "Can’t proceed to payment, please remove promo code",
      "Tidak bisa melanjutkan ke halaman pembayaran, mohon hilangkan promo code"),
  PARAM_NOT_FOUND_IN_VARIABLES("PARAM_NOT_FOUND_IN_VARIABLES", "Param not found in variable", ""),
  CAMPAIGN_ACTIVE_IS_EMPTY("CAMPAIGN_ACTIVE_IS_EMPTY", "Campaign Active Is Empty", ""),
  PROMO_CODE_ADJUSTMENT_ACTIVE_IS_EMPTY("PROMO_CODE_ADJUSTMENT_ACTIVE_IS_EMPTY",
      "Promo Code Adjustment Active Is Empty", ""),
  PROMO_CODE_ADJUSTMENT_STATUS_NOT_PENDING("PROMO_CODE_ADJUSTMENT_STATUS_NOT_PENDING",
      "Promo Code Adjustment Status is Not Pending", "Promo Code Adjustment Status tidak Pending"),
  BUSINESS_LOGIC_RESPONSE_ACTIVE_IS_EMPTY("BUSINESS_LOGIC_RESPONSES_ACTIVE_IS_EMPTY",
      "Business Logic Response Active is Empty", ""),
  BUSINESS_LOGIC_RESPONSE_NOT_EXIST("BUSINESS_LOGIC_RESPONSE_NOT_EXIST",
      "Business Logic Response not exist", ""),
  //Period Related
  PERIOD_NOT_VALID("PERIOD_NOT_VALID", "Promo code isn’t available", "Kode promo tidak tersedia."),
  PERIOD_NOT_VALID_FOR_NOW("PERIOD_NOT_VALID_FOR_NOW", "You can’t use this promo code today",
      "Kode promo tidak berlaku hari ini "),
  STORE_ID_NOT_EXIST("", "", ""),
  STORE_ID_LIST_NOT_EXIST("", "", ""),
  STORE_ID_EXIST("", "", ""),
  CHANNEL_ID_NOT_EXIST("", "", ""),
  CHANNEL_ID_LIST_NOT_EXIST("", "", ""),
  CHANNEL_ID_EXIST("", "", ""),
  PRODUCT_TYPE_LIST_NOT_EXIST("", "", ""),
  PRODUCT_TYPE_EXIST("", "", ""),
  FAILED_INSERT_LOG("", "", ""),
  MINIMUM_PAYMENT_NOT_VALID("MINIMUM_PAYMENT_NOT_VALID", "Minimum Payment not valid", "Minimum Payment not valid"),
  CAMPAIGN_NOT_EXIST_FIND_BY_ADJUSTMENT_ID("CAMPAIGN_NOT_EXIST_FIND_BY_ADJUSTMENT_ID", "campaign not exist when find by adjustmentId", "campaign tidak ada saat mencari menggunakan adjustmentId");


  private String code;
  private String message;
  private String messageEnglish;


  ResponseCode(String code, String message, String messageEnglish) {
    this.code = code;
    this.message = message;
    this.messageEnglish = messageEnglish;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getMessageEnglish() {
    return messageEnglish;
  }
}
