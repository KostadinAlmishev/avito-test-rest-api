package io.avito.taskservice.utils;

import java.util.Objects;

public class StringUtils {

    public static boolean compare(String str1, String str2) {
        str1 = str1 != null ? str1 : "";
        str2 = str2 != null ? str2 : "";

        return Objects.equals(str1, str2);
    }
}
