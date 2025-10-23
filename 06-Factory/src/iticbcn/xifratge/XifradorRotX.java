package iticbcn.xifratge;

public class XifradorRotX implements Xifrador{
    private char[] abc = "abcdefghijklmnopqrstuvwxyzçñáàèéíìïòóúùü".toCharArray();
    private char[] abcMayus = "ABCDEFGHIJKLMNOPQRSTUVWXYZÇÑÁÀÈÉÍÌÏÒÓÚÙÜ".toCharArray();

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        int rot = convertirClau(clau);
        String resultado = xifraRot13(msg, rot);
        return new TextXifrat(resultado.getBytes());
    }
    
    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int rot = convertirClau(clau);
        String texto = new String(xifrat.getBytes());
        return desxifraRot13(texto, rot);
    }
    
    private int convertirClau(String clau) throws ClauNoSuportada {
        try {
            return Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("XifradorRotX només admet claus numèriques");
        }
    }

    public String xifraRot13(String cadena, int rot) {
        char[] resultado = new char[cadena.length()];  
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            resultado[i] = c;
            for (int j = 0; j < abc.length; j++) {
                if (c == abc[j]) {
                    int newIndex = (j + rot) % abc.length;
                    if (newIndex < 0) {
                        newIndex += abc.length;
                    }
                    resultado[i] = abc[newIndex];
                    break;
                }
                if (c == abcMayus[j]) {
                    int newIndex = (j + rot) % abcMayus.length;
                    if (newIndex < 0) {
                        newIndex += abcMayus.length;
                    }
                    resultado[i] = abcMayus[newIndex];
                    break;
                }
            }
        }
        return new String(resultado);
    } 
    
    public String desxifraRot13(String cadena, int rot) {
        char[] resultado = new char[cadena.length()];
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            resultado[i] = c; 
            for (int j = 0; j < abc.length; j++) {
                if (c == abc[j]) {
                    int newIndex = (j - rot) % abc.length;
                    if (newIndex < 0) {
                        newIndex += abc.length;
                    }
                    resultado[i] = abc[newIndex];
                    break;
                }
                if (c == abcMayus[j]) {
                    int newIndex = (j - rot) % abcMayus.length;
                    if (newIndex < 0) {
                        newIndex += abcMayus.length;
                    }
                    resultado[i] = abcMayus[newIndex];
                    break;
                }
            }
        }
        return new String(resultado);
    }
    
    public String forcaBrutaRotX(String cadena) {
        char[] resultado = new char[cadena.length()];
        int rot = 0;
        while (rot <= 39) {      
            for (int i = 0; i < cadena.length(); i++) {
                char c = cadena.charAt(i);
                resultado[i] = c;  
                for (int j = 0; j < abc.length; j++) {
                    if (c == abc[j]) {
                        int newIndex = (j + rot) % abc.length;
                        if (newIndex < 0) {
                            newIndex += abc.length;
                        }
                        resultado[i] = abc[newIndex];
                        break;
                    }
                    if (c == abcMayus[j]) {
                        int newIndex = (j + rot) % abcMayus.length;
                        if (newIndex < 0) {
                            newIndex += abcMayus.length;
                        }
                        resultado[i] = abcMayus[newIndex];
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