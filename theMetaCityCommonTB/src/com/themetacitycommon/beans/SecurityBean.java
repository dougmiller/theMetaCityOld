package com.themetacitycommon.beans;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This is a class of security related functions.
 */
public class SecurityBean {

    /**
     *
     * @param toEncode is the string that needs to be encoded (eg the password)
     * @param salt is a security helping function to make it harder to guess passwords
     * @return an SHA-512 hash of hte salt+toEncode
     */
    public String getSHA512OfString(String toEncode, String salt) {
        StringBuilder finalHash = new StringBuilder();
        {
            try {
                byte[] theTextToDigestAsBytes = (salt+toEncode).getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-512");


                md.update(theTextToDigestAsBytes);
                byte[] digest = md.digest();

                // dump out the hash
                for (byte b : digest) {
                    finalHash.append(getByteString(b));
                }
            } catch (UnsupportedEncodingException unspportedEncoding) {
                System.out.println("Unsupported encoding for SHA function");
                System.out.println(unspportedEncoding);

            } catch (NoSuchAlgorithmException noSuchAlgorithm) {
                System.out.println("No such algorithm for hashing available");
                System.out.println(noSuchAlgorithm);
            }

        }
        return finalHash.toString();
    }

    private String getByteString(byte x) {
        String str = Integer.toHexString(x & 0xFF);
        if (str.length() == 0) {
            return "00";
        } else if (str.length() == 1) {
            return "0" + str;
        } else {
            return str;
        }
    }
}
