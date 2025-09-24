public class Rot13 {
    static String abc = "abcdefghijklmnopqrstuvwxyzçñáàéèíìóòúùïü";
    static String abcMayus = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÁÀÉÈÍÌÓÒÚÙÏÜ";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No hi ha cap text");
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
            System.out.println(xifraRot13(texto) + " => " + desxifraRot13(xifraRot13(texto)));
        }
    }

    public static String xifraRot13(String texto) {
        String resultado = "";
        int trece = 13;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            int idx = abc.indexOf(c);
            int idxMayus = abcMayus.indexOf(c);
            if (idx != -1) {
                int newIdx = idx + trece;
                while (newIdx >= abc.length()) newIdx -= abc.length();
                resultado += abc.charAt(newIdx);
            } else if (idxMayus != -1) {
                int newIdx = idxMayus + trece;
                while (newIdx >= abcMayus.length()) newIdx -= abcMayus.length();
                resultado += abcMayus.charAt(newIdx);
            } else {
                resultado += c;
            }
        }
        return resultado;
    }

    public static String desxifraRot13(String texto) {
        String resultado = "";
        int trece = 13;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            int idx = abc.indexOf(c);
            int idxMayus = abcMayus.indexOf(c);
            if (idx != -1) {
                int newIdx = idx - trece;
                while (newIdx < 0) newIdx += abc.length();
                resultado += abc.charAt(newIdx);
            } else if (idxMayus != -1) {
                int newIdx = idxMayus - trece;
                while (newIdx < 0) newIdx += abcMayus.length();
                resultado += abcMayus.charAt(newIdx);
            } else {
                resultado += c;
            }
        }
        return resultado;
    }
}