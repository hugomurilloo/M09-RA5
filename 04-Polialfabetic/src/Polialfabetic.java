import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Polialfabetic {
        static char[] alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÀÈÉÍÏÒÓÚÜ".toCharArray();
        static Random randomNumbers = new Random();
        static ArrayList<Character> alfabetList = new ArrayList<>();
        static char[] permutacio;



        static long clauSecreta = 8;
    public static void main(String[] args) {
        String msgs[] = {"Test 01 àrbritre, coixí, Perímetre",
        "Test 02 Taüll, DíA, año",
        "Test 03 Peça, Órrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n---");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("Desxifratge:\n---");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }
    public static void initRandom(long clauSecreta) {
        randomNumbers = new Random(clauSecreta); 
    }

    public static String xifraPoliAlfa(String msg){
        permutaAlfabet();
        char[] resultado = new char[msg.length()];
        
        for (int i = 0; i < msg.length(); i++) {
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

    public static String desxifraPoliAlfa(String msgXifrat){

        char[] resultado = new char[msgXifrat.length()];
        for (int i = 0; i < msgXifrat.length(); i++) {
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

    public static void permutaAlfabet(){
        alfabetList.clear();
        for(char c: alfabet){
            alfabetList.add(c);
        }
        Collections.shuffle(alfabetList, randomNumbers);
        permutacio = permutaAlfabet(alfabetList);
    }

    public static char[] permutaAlfabet(ArrayList<Character> lista) {
        char[] resultado = new char[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            resultado[i] = lista.get(i);
        }
        return resultado;
    }
}
