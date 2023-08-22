package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StringTools {
    public static Boolean isEmpty(String str) {
        return null == str || "".equals(str.trim()) || "null".equals(str);
    }

    public static final String getRandomString(Integer count) {
        return RandomStringUtils.random(count, true, true);
    }
}
