package com.planepockets.util;


import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UtilityClass {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");

    private static String createOrderDate() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(now);
    }

    public static String generateRandomPassword(int length) {
        String CHARACTERS = "ABCD0E1F2G3H4I5J6K7L8M9N!O@P#U$V%^W&*Z(X)Y-_=+[]{}|;:,.<>?/abcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public static String createNewDate() {
        Date d = new Date();
        return dateFormat.format(d);
    }

}
