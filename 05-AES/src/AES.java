import javax.crypto.*;
import java.security.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "LaClauSecretaQueVulquis";

    // XIFRAR
    public static byte[] xifraAES(String msg, String password) throws Exception{
    // Obtenir els bytes de l'String
    byte[] bytes = msg.getBytes("UTF-8");
    // Genera IvParameterSpec
    IvParameterSpec ivSpec = GenerarIvParameterSpec();
    byte[] ivBytes = ivSpec.getIV();
    // Genera hash de la password
    MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
    byte[] keyHash = digest.digest(password.getBytes("UTF-8"));
    // Encrypt.
    SecretKeySpec keySpec = new SecretKeySpec(keyHash, ALGORISME_XIFRAT);
    Cipher cipher = Cipher.getInstance(FORMAT_AES);
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
    byte[] encrypted = cipher.doFinal(bytes);
    // Combinar IV i part xifrada.
    byte[] result = new byte[ivBytes.length + encrypted.length];

    for (int i = 0; i < ivBytes.length; i++){ 
        result[i] = ivBytes[i];
    }

    for (int i = 0; i < encrypted.length; i++){
         result[ivBytes.length + i] = encrypted[i];
        }
    // return iv+msgxifrat
    return result;
    }

    // Genera IvParameterSpec
    public static IvParameterSpec GenerarIvParameterSpec(){
        SecureRandom randomSecureRandom = new SecureRandom();
        randomSecureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    //DESXIFRAR
    public static String desxifraAES(byte[] bMsgXifrat, String password) throws Exception{
    
    // Extreure l'IV.
    
    // Extreure la part xifrada.
    
    // Fer hash de la clau
    
    // Desxifrar.
    
    // return String desxifrat


        return "EstoEstaEnProceso";
    }

    // MAIN
    public static void main(String[] args) {
    String msgs[] = {"Lorem ipsum dicet",
    "Hola Andrés cómo está tu cuñado",
    "Agora lila Ótto"};

    for (int i = 0; i < msgs.length; i++) {
        String msg = msgs[i];
        
        byte[] bXifrats = null;
        String desxifrat = "";
        try {
            bXifrats = xifraAES(msg, CLAU);
            desxifrat = desxifraAES(bXifrats, CLAU);
        } catch (Exception e) {
            System.err.println("Error de xifrat: " + e.getLocalizedMessage());
        }
        System.out.println("---");
        System.out.println("Msg: " + msg);
        System.out.println("Enc: " + new String(bXifrats));
        System.out.println("DEC: " + desxifrat);
        }
    }
}
