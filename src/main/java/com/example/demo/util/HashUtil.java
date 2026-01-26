package com.example.demo.util;

import org.jspecify.annotations.NonNull;

public class HashUtil {

    private static final String CHARS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // Base62 encoding
    public static @NonNull String generateShortKey(long number) {
        StringBuilder sb = new StringBuilder();

        while (number > 0) {
            sb.append(CHARS.charAt((int) (number % 62)));
            number /= 62;
        }

        return sb.reverse().toString();
    }
}
