package com.lutadam.healthsystem;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    public static String encrypt(String pass)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] passByte = md.digest(pass.getBytes());
        BigInteger bigInteger = new BigInteger(1,passByte);
        return bigInteger.toString(16);
    }

}
