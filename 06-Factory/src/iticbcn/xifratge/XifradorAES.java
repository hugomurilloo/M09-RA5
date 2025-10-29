package iticbcn.xifratge;
import javax.crypto.*;
import java.security.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    private static final int MIDA_IV = 16;
    
    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            byte[] resultado = xifraAES(msg, clau);
            return new TextXifrat(resultado);
        } catch (Exception e) {
            System.err.println("Error en xifrat AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
    
    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            return desxifraAES(xifrat.getBytes(), clau);
        } catch (Exception e) {
            System.err.println("Error en desxifrat AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }


    public byte[] xifraAES(String msg, String clau) throws Exception {
        byte[] bytes = msg.getBytes("UTF-8");
        IvParameterSpec ivSpec = generarIvParameterSpec();
        byte[] ivBytes = ivSpec.getIV();
        
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyHash = digest.digest(clau.getBytes("UTF-8"));
        
        SecretKeySpec keySpec = new SecretKeySpec(keyHash, ALGORISME_XIFRAT);
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(bytes);
        
        byte[] result = new byte[ivBytes.length + encrypted.length];
        System.arraycopy(ivBytes, 0, result, 0, ivBytes.length);
        System.arraycopy(encrypted, 0, result, ivBytes.length, encrypted.length);
        
        return result;
    }

    public String desxifraAES(byte[] bMsgXifrat, String clau) throws Exception {
        if (bMsgXifrat == null || bMsgXifrat.length <= MIDA_IV) {
            throw new IllegalArgumentException("Dades xifrades no vàlides");
        }

        byte[] ivBytes = new byte[MIDA_IV];
        System.arraycopy(bMsgXifrat, 0, ivBytes, 0, MIDA_IV);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        int encLen = bMsgXifrat.length - MIDA_IV;
        byte[] encrypted = new byte[encLen];
        System.arraycopy(bMsgXifrat, MIDA_IV, encrypted, 0, encLen);

        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] keyHash = digest.digest(clau.getBytes("UTF-8"));

        SecretKeySpec keySpec = new SecretKeySpec(keyHash, ALGORISME_XIFRAT);
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted, "UTF-8");
    }

    public IvParameterSpec generarIvParameterSpec(){
        byte[] iv = new byte[MIDA_IV];
        SecureRandom randomSecureRandom = new SecureRandom();
        randomSecureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}