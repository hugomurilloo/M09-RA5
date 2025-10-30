package iticbcn.xifratge;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

public class ClauPublica {
    public KeyPair generaParellClausRSA() throws Exception{
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    return keyPair;
    }
    public byte[] xifraRSA(String msg,PublicKey clauPublica) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, clauPublica);
        byte[] plain = msg.getBytes("UTF-8");
        return cipher.doFinal(plain);
    }
    public String desxifraRSA(byte[] msgXifrat, PrivateKey clauPrivada) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, clauPrivada);
        byte[] plain = cipher.doFinal(msgXifrat);
        return new String(plain, "UTF-8");
    }
}
