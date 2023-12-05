package com.creelayer.marketplace.crm.market.core;

import java.util.Random;

public class RandomString {

    public final static int DEFAULT_RANDOM_LENGTH = 40;

    public static String generate() {
        return generate(DEFAULT_RANDOM_LENGTH);
    }

    public static String generate(Integer length) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
