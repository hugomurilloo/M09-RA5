package src;
import java.util.ArrayList;
import java.util.Collections;

public class Monoalfabetic {
    //Variables
    static char[] alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÀÈÉÍÏÒÓÚÜ".toCharArray();
    static ArrayList<Character> alfabetList = new ArrayList<>();
    static char[] permutacio;

    // Agregar elements a la permutació
    static {
        for(char c: alfabet){
            alfabetList.add(c);
        }
        permutacio = permutaAlfabet(alfabetList);
    }

    //Main Proves
    public static void main(String[] args) {
        String[] pruebas = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        
        System.out.print("\nAlfabet: ");
        System.out.println(alfabet);
        System.out.print("Permutació: ");
        System.out.println(permutacio);
        System.out.println();
        
        System.out.println("Xifrat");
        System.out.println("---------");
        for (String p : pruebas) {
            System.out.println(p + " => " + xifraMonoAlfa(p));
        }
        
        System.out.println("\nDesxifrat");
        System.out.println("---------");
        for (String p : pruebas) {
            String cifrado = xifraMonoAlfa(p);
            System.out.println(cifrado + " => " + desxifraMonoAlfa(cifrado));
        }
        System.out.println(); 
    }

    //Permuta Alfabet
    public static char[] permutaAlfabet(ArrayList<Character> alfabet){
        Collections.shuffle(alfabet); 
        char[] resultado = new char[alfabet.size()];
        for (int i = 0; i < alfabet.size(); i++) {
            resultado[i] = alfabet.get(i);
        }
        return resultado;
    }
    
    //XifraMonoAlfa
    public static String xifraMonoAlfa(String cadena) {
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
   
    //DesxifraMonoAlfa
    public static String desxifraMonoAlfa(String cadena) {
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
