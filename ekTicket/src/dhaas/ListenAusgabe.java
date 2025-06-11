package dhaas;

import java.util.List;

public class ListenAusgabe implements Ausgabe {
    private final List<String> zeilen;

    public ListenAusgabe(List<String> zeilen) {
        this.zeilen = zeilen;
    }

    @Override
    public void sagen(String text) {
        zeilen.add(text);
    }
}
