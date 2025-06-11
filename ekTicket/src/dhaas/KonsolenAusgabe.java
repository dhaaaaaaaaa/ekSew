package dhaas;

public class KonsolenAusgabe implements Ausgabe {
    @Override
    public void sagen(String text) {
        System.out.println(text);
    }
}
