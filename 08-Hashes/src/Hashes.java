// PD: acaba lo del formato del tiempo
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
    
    // Força bruta per trobar la contrasenya
    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        npass = 0;
        String caracters = "abcdefABCDEF1234567890!";
        int midaMaxima = 6;
        
        // Prova totes les mides (1 - 6 carac) -- cambiar
        for (int mida = 1; mida <= midaMaxima; mida++) {

            String resultat = forcaBrutaRecursiu(alg, hash, salt, caracters, "", mida);
            if (resultat != null) {
                return resultat;
            }
        }
        
        return null;
    }
    
    // Força bruta
    private String forcaBrutaRecursiu(String alg, String hashObjectiu, String salt, 
                                     String caracters, String actual, int mida) throws Exception {
        if (actual.length() == mida) {
            npass++;
            
            // Calcula hash
            String hashProva;
            if (alg.equals("SHA-512")) {
                hashProva = getSHA512AmbSalt(actual, salt);
            } else {
                hashProva = getPBKDF2AmbSalt(actual, salt);
            }
            
            // Comprova si coincideix
            if (hashProva.equals(hashObjectiu)) {
                return actual;
            }
            return null;
        }
        
        // Prova tots els caracters -- cambiar
        for (int i = 0; i < caracters.length(); i++) {
            char c = caracters.charAt(i);
            String resultat = forcaBrutaRecursiu(alg, hashObjectiu, salt, caracters, actual + c, mida);
            if (resultat != null) {
                return resultat;
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