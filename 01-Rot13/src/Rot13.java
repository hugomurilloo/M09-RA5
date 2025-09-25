public class Rot13 {
    //Variables
    static char[] abc = "abcdefghijklmnopqrstuvwxyzçñàèéíïòóúü".toCharArray();
    static char[] abcMayus = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÀÈÉÍÏÒÓÚÜ".toCharArray();
    
    //Main
    public static void main(String[] args) {
        String[] pruebas = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        
        System.out.println("Xifrat");
        System.out.println("---------");
        for (String p : pruebas) {
            System.out.println(p + " => " + xifraRot13(p));
        }
        
        System.out.println("\nDesxifrat");
        System.out.println("---------");
        for (String p : pruebas) {
            String cifrado = xifraRot13(p);
            System.out.println(cifrado + " => " + desxifraRot13(cifrado));
        }
    }
    // Metodo
    public static String xifraRot13(String cadena) {
        char[] resultado = new char[cadena.length()];
        
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            resultado[i] = c;
            
            for (int j = 0; j < abc.length; j++) {
                if (c == abc[j]) {
                    resultado[i] = abc[(j + 13) % abc.length];
                    break;
                }
                if (c == abcMayus[j]) {
                    resultado[i] = abcMayus[(j + 13) % abcMayus.length];
                    break;
                }
            }
        }
        return new String(resultado);
    }
   
    // Metodo
    public static String desxifraRot13(String cadena) {
        char[] resultado = new char[cadena.length()];
        
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            resultado[i] = c;
            
            for (int j = 0; j < abc.length; j++) {
                if (c == abc[j]) {
                    resultado[i] = abc[(j - 13 + abc.length) % abc.length];
                    break;
                }
                if (c == abcMayus[j]) {
                    resultado[i] = abcMayus[(j - 13 + abcMayus.length) % abcMayus.length];
                    break;
                }
            }
        }
        return new String(resultado);
    }

    

}