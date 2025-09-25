public class RotX {
    //Variables
    static char[] abc = "abcdefghijklmnopqrstuvwxyzçñáàèéíìïòóúùü".toCharArray();
    static char[] abcMayus = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÁÀÈÉÍÌÏÒÓÚÙÜ".toCharArray();
    //Main
    public static void main(String[] args) {
        String[] pruebas = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        int[] rotacions = {0,2,4,6};
        String frase = "Ìwòvo, ìwò úù ïùi jó?";
        System.out.println("Xifrat");
        System.out.println("---------");
        for (int i = 0; i < pruebas.length && i < rotacions.length; i++) {
            String p = pruebas[i];
            int r = rotacions[i];
            System.out.println("(" + r + ")-" + p + " => " + xifraRot13(p, r));
        } 
        System.out.println("\nDesxifrat");
        System.out.println("---------");
        for (int i = 0; i < pruebas.length && i < rotacions.length; i++) {
            String p = pruebas[i];
            int r = rotacions[i];
            String cifrado = xifraRot13(p,r);
            System.out.println("(" + r + ")-" + cifrado + " => " + desxifraRot13(cifrado, r));
        }
        System.out.println("\nMissatge xifrat: " + frase);
        System.out.println("----------------");
        System.out.println(forcaBrutaRotX(frase));
    }
    // Metodo
    public static String xifraRot13(String cadena, int rot) {
        char[] resultado = new char[cadena.length()];  
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            resultado[i] = c;
            for (int j = 0; j < abc.length; j++) {
                if (c == abc[j]) {
                    resultado[i] = abc[(j + rot) % abc.length];
                    break;
                }
                if (c == abcMayus[j]) {
                    resultado[i] = abcMayus[(j + rot) % abcMayus.length];
                    break;
                }
            }
        }
        return new String(resultado);
    } 
    // Metodo
    public static String desxifraRot13(String cadena, int rot) {
        char[] resultado = new char[cadena.length()];
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            resultado[i] = c; 
            for (int j = 0; j < abc.length; j++) {
                if (c == abc[j]) {
                    resultado[i] = abc[(j - rot + abc.length) % abc.length];
                    break;
                }
                if (c == abcMayus[j]) {
                    resultado[i] = abcMayus[(j - rot + abcMayus.length) % abcMayus.length];
                    break;
                }
            }
        }
        return new String(resultado);
    }
    //Metodo
    public static String forcaBrutaRotX(String cadena) {
        char[] resultado = new char[cadena.length()];
        int rot = 0;
        while (rot <= 39) {      
            for (int i = 0; i < cadena.length(); i++) {
                char c = cadena.charAt(i);
                resultado[i] = c;  
                for (int j = 0; j < abc.length; j++) {
                    if (c == abc[j]) {
                        resultado[i] = abc[(j + rot) % abc.length];
                        break;
                    }
                    if (c == abcMayus[j]) {
                        resultado[i] = abcMayus[(j + rot) % abcMayus.length];
                        break;
                    }
                }
            }
            rot++;
            System.out.println("(" + (rot-1) + ") -> " + new String(resultado)); 
        }
        return "";
    }     
}