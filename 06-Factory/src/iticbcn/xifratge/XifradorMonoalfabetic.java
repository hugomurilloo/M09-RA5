package iticbcn.xifratge;
import java.util.ArrayList;
import java.util.Collections;

public class XifradorMonoalfabetic implements Xifrador{
    private char[] alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÀÈÉÍÏÒÓÚÜ".toCharArray();
    private ArrayList<Character> alfabetList = new ArrayList<>();
    private char[] permutacio;

    public XifradorMonoalfabetic() {
        for(char c: alfabet){
            alfabetList.add(c);
        }
        permutacio = permutaAlfabet(alfabetList);
    }

    // Permuta Alfabet
    public char[] permutaAlfabet(ArrayList<Character> alfabet){
        Collections.shuffle(alfabet); 
        char[] resultado = new char[alfabet.size()];
        for (int i = 0; i < alfabet.size(); i++) {
            resultado[i] = alfabet.get(i);
        }
        return resultado;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("XifradorMonoalfabetic no admet clau");
        }
        String resultado = xifraMonoAlfa(msg);
        return new TextXifrat(resultado.getBytes());
    }
    
    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("XifradorMonoalfabetic no admet clau");
        }
        String texto = new String(xifrat.getBytes());
        return desxifraMonoAlfa(texto);
    }
    
    // XifraMonoAlfa
    public String xifraMonoAlfa(String cadena) {
        char[] resultado = new char[cadena.length()];
        
        for (int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
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
   
    // DesxifraMonoAlfa
    public String desxifraMonoAlfa(String cadena) {
        char[] resultado = new char[cadena.length()];
        for (int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
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
}