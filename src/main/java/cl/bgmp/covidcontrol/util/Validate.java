package cl.bgmp.covidcontrol.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
  public static boolean isValidRUT(String rut) {
    String[] segments = rut.split("-");
    if (segments.length != 2) return false;

    String[] numberSegments = segments[0].split("\\.");
    if (numberSegments.length != 3) return false;

    StringBuilder numberBuilder = new StringBuilder();
    for (String numberSegment : numberSegments) {
      numberBuilder.append(numberSegment);
    }

    int number;
    try {
      number = Integer.parseInt(numberBuilder.toString());
    } catch (NumberFormatException ignore) {
      return false;
    }
    char verifier = segments[1].toCharArray()[0];

    boolean valid = false;
    int m = 0, s = 1;
    for (; number != 0; number /= 10) {
      s = (s + number % 10 * (9 - m++ % 6)) % 11;
    }
    if (verifier == (s != 0 ? s + 47 : 75)) {
      valid = true;
    }

    return valid;
  }

  public static boolean isValidMobileNumber(String str) {
    Pattern ptrn = Pattern.compile("(9)?[0-9]{9}");
    Matcher match = ptrn.matcher(str);

    return (match.find() && match.group().equals(str));
  }

  public static boolean isValidEmail(String email) {
    String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    return email.matches(regex);
  }
}
