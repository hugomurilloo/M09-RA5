import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    // Comptador de contrasenyes provades
    public int npass = 0;
    
    // Genera hash SHA-512
    public String getSHA512AmbSalt(String pw, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        String combinat = pw + salt;

        byte[] hash = md.digest(combinat.getBytes());
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash);
    }
    
    // Genera hash PBKDF2
    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        int iteracions = 10000;

        int midaClau = 128;
        KeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), iteracions, midaClau);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        
        byte[] hash = factory.generateSecret(spec).getEncoded();
        
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash);
    }
    
    // Mètode auxiliar per trobar password
    private String pwTrobat(String alg, char[] aPw, int pos, char c, String hash, String salt) throws Exception {
        aPw[pos] = c;
        
        if (pos == aPw.length - 1) {
            // Tenim un password complet
            npass++;
            String passwordActual = new String(aPw);
            
            // Calcula hash
            String hashProva;
            if (alg.equals("SHA-512")) {
                hashProva = getSHA512AmbSalt(passwordActual, salt);
            } else {
                hashProva = getPBKDF2AmbSalt(passwordActual, salt);
            }
            
            // Comprova si coincideix
            if (hashProva.equals(hash)) {
                return passwordActual;
            }
        } else {
            // Seguent posicio
            String charset = "abcdefABCDEF1234567890!";
            for (int i = 0; i < charset.length(); i++) {
                String result = pwTrobat(alg, aPw, pos + 1, charset.charAt(i), hash, salt);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
    
    // Força bruta per trobar la contrasenya
    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        npass = 0;
        String charset = "abcdefABCDEF1234567890!";
        String pw = null;

        // Prova totes les longituds de 6 a 1 (asi tarda menos comienza de largo a pequeño)
        for (int len = 6; len >= 1; len--) {
            char[] aPw = new char[len];
            
            for (int i0 = 0; i0 < charset.length(); i0++) {
                if ((pw = pwTrobat(alg, aPw, 0, charset.charAt(i0), hash, salt)) != null)
                    return pw;
            }
        }
        
        return null;
    }
    
    // Formata el temps -- acabado
    public String getInterval(long t1, long t2) {
        long diferencia = t2 - t1;
        long millis = diferencia % 1000;
        long segons = (diferencia / 1000) % 60;
        long minuts = (diferencia / (1000 * 60)) % 60;
        long hores = (diferencia / (1000 * 60 * 60)) % 24;
        long dies = diferencia / (1000 * 60 * 60 * 24);
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis", dies, hores, minuts, segons, millis);
    }
    
    // MAIN
    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        
        String[] hashs = { 
            h.getSHA512AmbSalt(pw, salt), 
            h.getPBKDF2AmbSalt(pw, salt) 
        };
        
        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};
        
        for (int i = 0; i < hashs.length; i++) {
            System.out.printf("========\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", hashs[i]);

            System.out.printf(" --\n");
            System.out.printf("-- Inici de força bruta ---\n");
            
            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], hashs[i], salt);
            long t2 = System.currentTimeMillis();
            
            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
    
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("\n\n");
        }
    }
}