package iticbcn.xifratge;

public class TextXifrat {
    private byte[] dadesXifrades;
    
    public TextXifrat(byte[] dadesXifrades) {
        this.dadesXifrades = dadesXifrades;
    }
    
    public byte[] getBytes() {
        return dadesXifrades;
    }
    
    @Override
    public String toString() {
        return new String(dadesXifrades);
    }
}