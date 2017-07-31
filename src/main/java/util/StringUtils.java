package util;

public class StringUtils {
    public static String emptyIfNull(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }
}
