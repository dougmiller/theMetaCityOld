package com.themetacity.utilities;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This is a class of security related functions.
 */
public class SecurityBean {

    static Logger logger = Logger.getLogger(SecurityBean.class);

    /**
     * @param toHash is the string that needs to be hashed (eg the password)
     * @param salt     is a security helping function to make it harder to guess passwords using rainbow tables
     * @return an SHA-512 hash of the salt+toEncode
     */
    public String hashPasswordWithSalt(String toHash, String salt) {
        // Take the hash and salt it and then hash the reulting concatination.
        // The salt will almost always be the username. This is to make rainbow attacks unfeasable.
        // Salt the hash to make it long and random enough to be a massive pain to decode
        return do512Hash(do512Hash(salt) + toHash);
    }

    // The actual hashing function
    private String do512Hash(String toHash) {
        StringBuilder finalHash = new StringBuilder();
        {
            try {
                byte[] theTextToDigestAsBytes = (toHash).getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-512");

                md.update(theTextToDigestAsBytes);
                byte[] digest = md.digest();

                // dump out the hash
                for (byte b : digest) {
                    finalHash.append(getByteString(b));
                }
            } catch (UnsupportedEncodingException unspportedEncoding) {
                logger.warn("Unsupported encoding for SHA function");
                logger.warn(unspportedEncoding);

            } catch (NoSuchAlgorithmException noSuchAlgorithm) {
                logger.warn("No such algorithm for hashing available");
                logger.warn(noSuchAlgorithm);
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
        }
        return str;

    }
}
