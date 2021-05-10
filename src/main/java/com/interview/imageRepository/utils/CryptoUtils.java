package com.interview.imageRepository.utils;

import com.interview.imageRepository.exception.CryptoException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class that encrypts and decrypts the image getting stored in database. Modified as
 * per need and reference taken from Citation : www.codejava.net
 */
public class CryptoUtils {
  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES";

  public static byte[] encrypt(String key, byte[] inputBytes)
          throws CryptoException {
     return doCrypto(Cipher.ENCRYPT_MODE, key, inputBytes);
  }

  public static byte[] decrypt(String key, byte[] inputBytes)
          throws CryptoException {
    return doCrypto(Cipher.DECRYPT_MODE, key, inputBytes);
  }

  private static byte[] doCrypto(int cipherMode, String key, byte[] inputBytes) throws CryptoException {
    try {
//      KeyGenerator generator = KeyGenerator.getInstance("AES");
//      generator.init(128);
//      System.out.println(generator.generateKey().getEncoded());
      Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(cipherMode, secretKey);

      return cipher.doFinal(inputBytes);

    } catch (NoSuchPaddingException | NoSuchAlgorithmException
            | InvalidKeyException | BadPaddingException
            | IllegalBlockSizeException ex) {
      throw new CryptoException("Error encrypting/decrypting file", ex);
    }
  }
}
