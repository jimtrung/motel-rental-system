package util;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenUtil {
  private static final SecureRandom random = new SecureRandom();

  public static String generateToken(int bytes) {
    byte[] buffer = new byte[bytes];
    random.nextBytes(buffer);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer);
  }
}
