public class Rot13 {
    static String abc = "abcçdefghijklmnñopqrstuvwxyzáàéèíìóòúùïü";
    static String abcMayus ="AÁÀBCÇDEÉÈFGHIÍÌÏJKLMNÑOÓÒPQRSTUÚÙÜVWXYZ";
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No hi ha cap text a xifrar/desxifrar");
            return;
        }

        System.out.println("Xifrat");
        System.out.println("---------");
        for (String texto : args) {
            System.out.println(texto + " => " + xifraRot13(texto));
        }

        System.out.println();

        System.out.println("Desxifrat");
        System.out.println("---------");
        for (String texto : args) {
            System.out.println(texto + " => " + desxifraRot13(texto));
        }
    }
    public static String xifraRot13(String texto) {
        char[] abcArray = abc.toCharArray();
        char[] abcMayusArray = abcMayus.toCharArray(); 
        String resultado = "";
        int trece = 13;
        for(int i=0; i<texto.length(); i++) {
            char c = texto.charAt(i);
            boolean trobat = false;
            for (int j = 0; j < abcArray.length; j++) {
                if (c == abcArray[j]) {
                    int index = j + trece;
                    while (index >= abcArray.length) index -= abcArray.length;
                    resultado += abcArray[index];
                    trobat = true;
                    break;
                }
            }
        }
        return resultado;
    }
    public static  String desxifraRot13(String texto) {
        char[] abcArray = abc.toCharArray();
        char[] abcMayusArray = abcMayus.toCharArray();
        String resultado = "";
        return "";
    }
}
