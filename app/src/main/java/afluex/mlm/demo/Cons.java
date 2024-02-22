package afluex.mlm.demo;

import android.util.Base64;



import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cons {
    // TODO BILL PAYMENT TYPE.......
    public static final String ELECTRICITY_BILL_PAYMENT = "Electricity Bill Payment";
    public static final String GAS_BILL_PAYMENT = "Gas Bill Payment";
    public static final String WATER_BILL_PAYMENT = "Water Bill Payment";
    public static final String INSURANCE_BILL_PAYMENT = "Insurance";
    public static final String BROADBAND_BILL_PAYMENT = "Broad Band Provider";
    //////////////////////////////////AES MODULE CBC/////////////////////////////////////////////////
    private static final String TAG = "AESCrypt";
    //AESCrypt-ObjC uses CBC and PKCS7Padding
    private static final String AES_MODE = "AES/CBC/PKCS7Padding";
    private static final String CHARSET = "UTF-8";
    //AESCrypt-ObjC uses SHA-256 (and so a 256-bit key)
    private static final String HASH_ALGORITHM = "SHA-256";
    //AESCrypt-ObjC uses blank IV (not the best security, but the aim here is compatibility)
    private static final byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    //togglable log option (please turn off in live!)
    public static boolean DEBUG_LOG_ENABLED = false;
    public static final int VERIFYPIN = 191;

    public static String calculateHmac(String payload, String secretKey) {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            byte[] result = mac.doFinal(payload.getBytes());
            return Base64.encodeToString(result, 0);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Exception hashing payload", e);
        }
    }

    /**
     * Encrypt and encode message using 256-bit AES with key generated from password.
     *
     * @param password used to generated key
     * @param message  the thing you want to encrypt assumed String UTF-8
     * @return Base64 encoded CipherText
     * @throws GeneralSecurityException if problems occur during encryption
     */
    public static String encryptMsg(String message, final SecretKey password)
            throws GeneralSecurityException {

        try {
            if (message != null && message.length() > 0) {
                byte[] cipherText = encrypt(password, ivBytes, message.getBytes(CHARSET));
                //NO_WRAP is important as was getting \n at the end
                return Base64.encodeToString(cipherText, Base64.NO_WRAP);
            } else {
                return "";
            }
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        }
    }


    /**
     * More flexible AES encrypt that doesn't encode
     *
     * @param key     AES key typically 128, 192 or 256 bit
     * @param iv      Initiation Vector
     * @param message in bytes (assumed it's already been decoded)
     * @return Encrypted cipher text (not encoded)
     * @throws GeneralSecurityException if something goes wrong during encryption
     */
    public static byte[] encrypt(final SecretKey key, final byte[] iv, final byte[] message)
            throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(AES_MODE);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] cipherText = cipher.doFinal(message);

        return cipherText;
    }


    /**
     * Decrypt and decode ciphertext using 256-bit AES with key generated from password
     *
     * @param password                used to generated key
     * @param base64EncodedCipherText the encrpyted message encoded with base64
     * @return message in Plain text (String UTF-8)
     * @throws GeneralSecurityException if there's an issue decrypting
     */
    public static String decryptMsg(String base64EncodedCipherText, final SecretKey password)
            throws GeneralSecurityException {

        try {
            if (base64EncodedCipherText != null && base64EncodedCipherText.length() > 0) {
                byte[] decodedCipherText = Base64.decode(base64EncodedCipherText, Base64.NO_WRAP);
                byte[] decryptedBytes = decrypt(password, ivBytes, decodedCipherText);
                String message = new String(decryptedBytes, CHARSET);
                return message;
            } else
                return "";


        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        }
    }


    /**
     * More flexible AES decrypt that doesn't encode
     *
     * @param key               AES key typically 128, 192 or 256 bit
     * @param iv                Initiation Vector
     * @param decodedCipherText in bytes (assumed it's already been decoded)
     * @return Decrypted message cipher text (not encoded)
     * @throws GeneralSecurityException if something goes wrong during encryption
     */
    public static byte[] decrypt(final SecretKey key, final byte[] iv, final byte[] decodedCipherText)
            throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(AES_MODE);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        return cipher.doFinal(decodedCipherText);
    }


    /**
     * Converts byte array to hexidecimal useful for logging and fault finding
     *
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

//    public static List<ResponseJioBrowsePlanPrepaid> responseJioBrowsePlanArray;
//    public static List<AllProviderlistItem> allProviderlistItemList;
//    public static List<GetAllProviderStateWiseListItem> allElectricityProviderlist;

}

