package iticbcn.xifratge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador{
    private char[] alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÀÈÉÍÏÒÓÚÜ".toCharArray();
    private Random randomNumbers = new Random();
    private ArrayList<Character> alfabetList = new ArrayList<>();
    private char[] permutacio;  
    private long clauSecreta = 8;

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        long clauSecreta = convertirClau(clau);
        initRandom(clauSecreta);
        String resultado = xifraPoliAlfa(msg);
        return new TextXifrat(resultado.getBytes());
    }
    
    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        long clauSecreta = convertirClau(clau);
        initRandom(clauSecreta);
        String texto = new String(xifrat.getBytes());
        return desxifraPoliAlfa(texto);
    }
    
    private long convertirClau(String clau) throws ClauNoSuportada {
        try {
            return Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("XifradorPolialfabetic només admet claus numèriques");
        }
    }

    public void initRandom(long clauSecreta) {
        this.clauSecreta = clauSecreta;
        randomNumbers = new Random(clauSecreta); 
    }

    public String xifraPoliAlfa(String msg){
        char[] resultado = new char[msg.length()];
        
        for (int i = 0; i < msg.length(); i++) {
            permutaAlfabet();
            
            char caracter = msg.charAt(i);
            char caracterMayus = Character.toUpperCase(caracter);

            int indice = -1;
            for (int j = 0; j < alfabet.length; j++) {
                if (alfabet[j] == caracterMayus) {
                    indice = j;
                    break;
                }
            }
            
            if (indice != -1) {
                char cifrado = permutacio[indice];
                if (Character.isLowerCase(caracter)) {
                    resultado[i] = Character.toLowerCase(cifrado);
                } else {
                    resultado[i] = cifrado;
                }
            } else {
                resultado[i] = caracter;
            }
        }
        
        return new String(resultado);
    }

    public String desxifraPoliAlfa(String msgXifrat){
        char[] resultado = new char[msgXifrat.length()];
        
        for (int i = 0; i < msgXifrat.length(); i++) {
            permutaAlfabet();
            
            char caracter = msgXifrat.charAt(i);
            char caracterMayus = Character.toUpperCase(caracter);
            
            int indice = -1;
            for (int j = 0; j < permutacio.length; j++) {
                if (permutacio[j] == caracterMayus) {
                    indice = j;
                    break;
                }
            }
            
            if (indice != -1) {
                char descifrado = alfabet[indice];
                if (Character.isLowerCase(caracter)) {
                    resultado[i] = Character.toLowerCase(descifrado);
                } else {
                    resultado[i] = descifrado;
                }
            } else {
                resultado[i] = caracter;
            }
        }
        
        return new String(resultado);  
    }

    public void permutaAlfabet(){
        alfabetList.clear();
        for(char c : alfabet){
            alfabetList.add(c);
        }
        Collections.shuffle(alfabetList, randomNumbers);
        
        permutacio = new char[alfabetList.size()];
        for (int i = 0; i < alfabetList.size(); i++) {
            permutacio[i] = alfabetList.get(i);
        }
    }
}