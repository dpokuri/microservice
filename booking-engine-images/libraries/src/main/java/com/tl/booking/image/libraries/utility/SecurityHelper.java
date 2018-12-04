package com.tl.booking.image.libraries.utility;

import com.tl.booking.image.entity.constant.enums.EncryptionType;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);

  private SecurityHelper() {
  }

  public static String encrypt(String text, String encryptionType) {
    try {
      MessageDigest crypt = MessageDigest.getInstance(encryptionType);

      if(encryptionType.equals(EncryptionType.SHA1.getValue())) {
        crypt.reset();
        crypt.update(text.getBytes("UTF-8"));
        return new BigInteger(1, crypt.digest()).toString(16);
      } else {
        byte[] array = crypt.digest(text.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
      }
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      LOGGER.error("{} Failed to parse. {}", e);
      return null;
    }
  }
}
