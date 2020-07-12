package com.goms.infrastructure.utility;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class RSAUtil {
  private static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
  private static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";
  private static final String LINE_SEPARATOR = System.lineSeparator();
  private static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
  private static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";

  public static void main(String[] args)
      throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException,
          NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
          InvalidKeyException, InvalidKeySpecException {

    String privateKey = "";
    BufferedReader br =
        new BufferedReader(
            new FileReader(
                "D:\\Code\\slash-paper\\sp-back-end\\src\\main\\resources\\private.txt"));
    String line;
    while ((line = br.readLine()) != null) {
      privateKey += line + "\n";
    }
    br.close();

    String publicKey = "";
    BufferedReader br2 =
        new BufferedReader(
            new FileReader("D:\\Code\\slash-paper\\sp-back-end\\src\\main\\resources\\public.txt"));
    String line2;
    while ((line2 = br2.readLine()) != null) {
      publicKey += line2 + "\n";
    }
    br2.close();

    System.out.println(
        new String(Base64.getEncoder().encode(encrypt("123456789ABCDEFG".getBytes(), publicKey))));
  }

  public static byte[] encrypt(byte[] message, String publicKeyInput)
      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
          IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException,
          InvalidAlgorithmParameterException {

    // Extract public key
    String keyText =
        publicKeyInput
            .replace(BEGIN_PUBLIC_KEY + LINE_SEPARATOR, "")
            .replace(LINE_SEPARATOR + END_PUBLIC_KEY, "")
            .replace(BEGIN_PUBLIC_KEY + "\n", "")
            .replace("\n" + END_PUBLIC_KEY, "");

    // Retrieve public to its original form
    byte[] publicKeyBytes = Base64.getMimeDecoder().decode(keyText);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

    // Encrypting using variation of OAEP with Hash = SHA256 and MGF1 = SHA256
    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
    OAEPParameterSpec oaepParams =
        new OAEPParameterSpec(
            "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
    cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParams);
    return cipher.doFinal(message);
  }

  public static byte[] decrypt(byte[] encryptedMessage, String privateKeyInput)
      throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
          IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException,
          InvalidAlgorithmParameterException {

    PrivateKey privateKey = extractPrivateKeyFromPEM(privateKeyInput);
    // Decrypting using variation of OAEP with Hash = SHA256 and MGF1 = SHA256
    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
    OAEPParameterSpec oaepParams =
        new OAEPParameterSpec(
            "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
    cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);
    return cipher.doFinal(encryptedMessage);
  }

  private static PrivateKey extractPrivateKeyFromPEM(String privateKeyInput)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    // Extract private key
    String keyText =
        privateKeyInput
            .replace(BEGIN_PRIVATE_KEY + LINE_SEPARATOR, "")
            .replace(LINE_SEPARATOR + END_PRIVATE_KEY, "")
            .replace(BEGIN_PRIVATE_KEY + "\n", "")
            .replace("\n" + END_PRIVATE_KEY, "");

    // Retrieve private to its original form
    byte[] privateKeyBytes = Base64.getMimeDecoder().decode(keyText);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
  }

  private static String publicKeyPEM(PublicKey publicKey) {
    final Base64.Encoder encoder = Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());
    String publicKeyString = encoder.encodeToString(publicKey.getEncoded());
    return BEGIN_PUBLIC_KEY + LINE_SEPARATOR + publicKeyString + LINE_SEPARATOR + END_PUBLIC_KEY;
  }

  private static String privateKeyPEM(PrivateKey privateKey) {
    final Base64.Encoder encoder = Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());
    String publicKeyString = encoder.encodeToString(privateKey.getEncoded());
    return BEGIN_PRIVATE_KEY + LINE_SEPARATOR + publicKeyString + LINE_SEPARATOR + END_PRIVATE_KEY;
  }

  private static String bytesToString(byte[] b) {
    byte[] b2 = new byte[b.length + 1];
    b2[0] = 1;
    System.arraycopy(b, 0, b2, 1, b.length);
    return new BigInteger(b2).toString(36);
  }

  private static byte[] stringToBytes(String s) {
    byte[] b2 = new BigInteger(s, 36).toByteArray();
    return Arrays.copyOfRange(b2, 1, b2.length);
  }

  private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048); // RSA Recommendation is 2048
    return keyPairGenerator.genKeyPair();
  }
}
