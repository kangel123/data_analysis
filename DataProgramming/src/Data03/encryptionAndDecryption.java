package Data03;


import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class encryptionAndDecryption {

   public static void main(String[] args) {
      String encryptedText = encrypt("aaaaaaaaaaaaaaaa","죽는 날까지 하늘을 우러러");
      System.out.println(encryptedText);
      String decryptedText = decrypt("aaaaaaaaaaaaaaaa",encryptedText);
      System.out.println(decryptedText);
   }

   public static String encrypt(String key, String text) {
      String cipherText="";
      try {
         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
         IvParameterSpec ivspec = new IvParameterSpec(Arrays.copyOfRange(key.getBytes("UTF-8"), 0, cipher.getBlockSize()));
         cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"),"AES"), ivspec);
         cipherText = new String(Base64.encodeBase64(cipher.doFinal(text.getBytes("UTF-8"))),"UTF-8");
      } catch (Exception e) {
         cipherText="";
         e.printStackTrace();
      }
      return cipherText;
   }


   public static String decrypt(String key, String encryptedText) {
      String plainText="";
      try {
         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
         IvParameterSpec ivspec = new IvParameterSpec(Arrays.copyOfRange(key.getBytes("UTF-8"), 0, cipher.getBlockSize()));
         cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"),"AES"), ivspec);
         plainText = new String(cipher.doFinal(Base64.decodeBase64(encryptedText.getBytes("UTF-8"))),"UTF-8");
      } catch (Exception e) {
         plainText="";
         e.printStackTrace();
      }
      return plainText;
   }

}