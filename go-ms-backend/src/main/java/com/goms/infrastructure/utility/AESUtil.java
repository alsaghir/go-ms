package com.goms.infrastructure.utility;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class AESUtil {
  private static final int GCM_IV_LENGTH = 12;
  private static final int GCM_TAG_LENGTH = 16;

  /**
   * Decrypts a message that's originally encoded using AES/GCM/NoPadding representation. See {@link
   * AESUtil#encrypt(byte[], SecretKeySpec, SecureRandom)}
   *
   * @param encryptedMessage encrypted message
   * @return the decoded message
   * @throws NoSuchPaddingException if PKCS5Padding is not available
   * @throws NoSuchAlgorithmException if AES is not available
   * @throws InvalidKeyException key length must be 16 for 128 bit size key
   * @throws BadPaddingException if PKCS5Padding is not available or not compatible
   * @throws IllegalBlockSizeException if the total input length of the data processed by this
   *     cipher is not a multiple of block size or if this encryption algorithm is unable to process
   *     the input data provided
   * @throws InvalidAlgorithmParameterException if invalid or inappropriate algorithm parameters
   *     passed
   */
  public static byte[] decrypt(byte[] encryptedMessage, SecretKeySpec secretKeySpec)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException {

    // getting  cipher which will be used for decrypting
    Cipher decryptCipher = Cipher.getInstance("AES/GCM/NoPadding");

    // 128 bit auth tag length and 12 is iv size. iv extracted
    // from encrypted message using iv's length
    GCMParameterSpec params =
        new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, encryptedMessage, 0, GCM_IV_LENGTH);
    decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, params);

    // decrypt the message noting that 12 is iv size
    return decryptCipher.doFinal(
        encryptedMessage, GCM_IV_LENGTH, encryptedMessage.length - GCM_IV_LENGTH);
  }

  private static byte[] decodeFromBase64(String input) {
    return Base64.getDecoder().decode(input);
  }

  public static void main(String[] args)
          throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
          IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {

    SecretKeySpec secret = new SecretKeySpec("123456789ABCDEFG".getBytes(), "AES");
String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoQM3evv+Ye+bb8TMpedV\n" +
        "owj7KZq3onky/qPwtVIYUNQDHM/icYL79s7Y8pav6e3Ikoimy+QIHloONP34za2s\n" +
        "7gZB+9n4iqEJBWNnCrt3FBe4BgJqUY/oJeUo+jM0e6vp0B9uk5QJzIEasK0R1ECL\n" +
        "U1BHA66tqI8JngZtOicOCDWSonuemvts1UCqmlnxMgJz6tgP1pqmPiAbpM8e8KJ9\n" +
        "mDoH+TON91mcYZO0mnY4/yqvclAUCnF58MPTOTQACU4gEQPw1NpoUOadg6xDoa9A\n" +
        "QfunsYiBy/639+QtZhsh8N9fR+UnFXirnNfvBovwjmUue/Cw3uGgOVMQk44hGyJw\n" +
        "IwIDAQAB\n" +
        "-----END PUBLIC KEY-----";

    byte[] encryptedSecret = RSAUtil.encrypt(secret.getEncoded(), publicKey);

    System.out.println(Base64.getEncoder().encodeToString(encryptedSecret));

    String privateKey = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQChAzd6+/5h75tv\n" +
            "xMyl51WjCPspmreieTL+o/C1UhhQ1AMcz+Jxgvv2ztjylq/p7ciSiKbL5AgeWg40\n" +
            "/fjNrazuBkH72fiKoQkFY2cKu3cUF7gGAmpRj+gl5Sj6MzR7q+nQH26TlAnMgRqw\n" +
            "rRHUQItTUEcDrq2ojwmeBm06Jw4INZKie56a+2zVQKqaWfEyAnPq2A/WmqY+IBuk\n" +
            "zx7won2YOgf5M433WZxhk7Sadjj/Kq9yUBQKcXnww9M5NAAJTiARA/DU2mhQ5p2D\n" +
            "rEOhr0BB+6exiIHL/rf35C1mGyHw319H5ScVeKuc1+8Gi/COZS578LDe4aA5UxCT\n" +
            "jiEbInAjAgMBAAECggEAWNVrgMETYnlOKuZLeqUdjGviFtwzwMJZrkBJB+EZZU7z\n" +
            "wKu5ZaM83LjW3VDiEZCNfrtCO2++QvCwsfAFm4Tcyh5NvWRPSjz6uyxSp4sycbV8\n" +
            "ZGRkwEdDb9T1PMWPiUQaJieRXH7qwRfh3+Q81/wcYoUCCTgQu9TfG360OMdfaIs0\n" +
            "gb59teNZAS5eVyCm+H/XtMz9OevHaGCttoU+LLG0bu9/Fr+gFh1maSmAHQQX3+XA\n" +
            "W4K2mDYIzluHNVaBaVOP1pX6mGVCu3xE8F9Ip8cw/BzJ5xYmEFwQFIcg1a3ITYPx\n" +
            "BYGlHtB0eTLksHJGNUW2k50rNSIZM90h9x+mKxuvMQKBgQDlR4vf6vzla2lDUykL\n" +
            "4x7Bb+s4dwNSO/JHKVByFcNhrn74AHE+4FPDS8Oq3wAIE8yoRsvHM/SbxOvq8JhP\n" +
            "CQGyU86GoBCFfxvjYXt6mT1PyQlz25oRpjaLsBGDY+RHXM4K6yldUdOh5Ro7NHfg\n" +
            "jUgvBQfjTsurtGzI2Zp0If/iDwKBgQCzxvTnKx9uP0t6GbOp0EOol4YJ+Z4H1yG8\n" +
            "lftHaaJeVVkIMjOOKSbAlZRjAgUWmIwzw+oC34u27lDERLdXOjwUZvJfaeAxvhLw\n" +
            "wjKlqvJ6psFrwTNgFiJXWfByftUtgTgKFRfPXk3UwvI1clXU2MWTHcmlHpkBF/MV\n" +
            "BG/D8xqUrQKBgQC0j0qVNX6hwZvs/CGYDe2bkmLgXcFM0o6zAdMrdP4gAAZXkimc\n" +
            "xZbRduoJt4JT2hvS7aXvI3hf742GwKCBpWsjn6JGHOnF59rzj0JpwyIoof6Csg88\n" +
            "FPRhv2+bVGBi3rAYoyc5KYaLuVuWAnThbwHInaKfnN8CQp1k8xESoe/cewKBgBzY\n" +
            "y+tcDAsQtPe+sAmLlY+z4JoxJ55ycsNj/ZIT7S3d3A9onsUs7T7xwYGxjUd26uhT\n" +
            "g3U6kZyk2OjlbdXdTk6nxjRxNLxGl5tyTdYvfAEaSOcVdTzx0Ejb8a0tlN9cYLWd\n" +
            "dNiddTeyoFLbKdKhhfJ5USXhpwN56FNzBswWcilxAoGBAIrHGt/bpoR268d3GVNG\n" +
            "owsDLHT1ZIryETawC4WS5XnGBvQXFVjUZ8QwWYJxnkidSKifsUHlPcEY4lWcvHN9\n" +
            "a0qBTDTAlfwrhsFEiaG1U/RSg351Pm64RdXyERysyownUCTer8G/L5QQp9J0aFRk\n" +
            "gXkR5BRDW57emP5GX+lY+85f\n" +
            "-----END PRIVATE KEY-----";

    byte[] decryptedSecret = RSAUtil.decrypt(encryptedSecret, privateKey);

    System.out.println(new String(decryptedSecret));

    byte[] encryptedPassword = encrypt("admin1".getBytes(), secret, new SecureRandom());

    System.out.println(Base64.getEncoder().encodeToString(encryptedPassword));

    byte[] decryptedPassword = decrypt(encryptedPassword, secret);

    System.out.println(new String(decryptedPassword));
  }

  /**
   * Encrypts a string using AES/GCM/NoPadding
   *
   * @param message The input plain message
   * @param secretKeySpec Used for encryption
   * @param secureRandom used for generating random bytes
   * @return Encoded String
   * @throws NoSuchPaddingException if PKCS5Padding is not available
   * @throws NoSuchAlgorithmException if AES is not available
   * @throws InvalidKeyException key length must be 16 for 128 bit size key
   * @throws BadPaddingException if PKCS5Padding is not available or not compatible
   * @throws IllegalBlockSizeException if the total input length of the data processed by this
   *     cipher is not a multiple of block size or if this encryption algorithm is unable to process
   *     the input data provided
   * @throws InvalidAlgorithmParameterException invalid or inappropriate algorithm parameters when
   *     creating {@link GCMParameterSpec}
   */
  public static byte[] encrypt(
      byte[] message, SecretKeySpec secretKeySpec, SecureRandom secureRandom)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
          BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

    // Creating initialization vector random byte-array is recommend
    // by NIST because it’s faster and more secure
    // Never reuse this IV with same key
    byte[] iv = new byte[GCM_IV_LENGTH];
    secureRandom.nextBytes(iv);

    // 128 bit auth tag length
    GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);

    // getting  cipher which will be used for encrypting
    final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);

    // Encrypt
    byte[] encodedInitially = cipher.doFinal(message);

    // To concat all of it to a single message, we create byte array with
    // 12 bytes is the iv length
    // original message length
    // 16 bytes is the auth tag length
    byte[] finalEncodedMsg = new byte[GCM_IV_LENGTH + message.length + GCM_TAG_LENGTH];

    // Concat all of it to a single message
    System.arraycopy(iv, 0, finalEncodedMsg, 0, 12);
    System.arraycopy(encodedInitially, 0, finalEncodedMsg, 12, encodedInitially.length);

    return finalEncodedMsg;
  }

  private static String encodeToBase64(byte[] finalEncodedMsg) {
    return Base64.getEncoder().encodeToString(finalEncodedMsg);
  }

  private static SecretKeySpec getSecretKeySpec(SecureRandom secureRandom) {
    // Creating the secret key to use for encryption.
    // Here 16 byte key means 128 bit which is recommended
    byte[] key = new byte[16];
    secureRandom.nextBytes(key);
    return new SecretKeySpec(key, "AES"); // Or better use PBEKeySpec
  }
}
