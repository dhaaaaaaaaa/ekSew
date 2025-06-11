package dhaas;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Ticketsystem system = new Ticketsystem();
        Ausgabe ausgabe = new KonsolenAusgabe();


        int kundenAnzahl = 4;
        int mitarbeiterAnzahl = 2;

        Thread[] kunden = new Thread[kundenAnzahl];
        for (int i = 0; i < kundenAnzahl; i++) {
            kunden[i] = new Thread(new Kunde("Kunde" + i, system, "Anliegen" + i, ausgabe));
            kunden[i].start();
        }

        Thread[] mitarbeiter = new Thread[mitarbeiterAnzahl];
        for (int i = 0; i < mitarbeiterAnzahl; i++) {
            mitarbeiter[i] = new Thread(new Mitarbeiter("Mitarbeiter" + i, system, ausgabe, kundenAnzahl));
            mitarbeiter[i].start();
        }

        for (int i = 0; i < kunden.length; i++) {
            try {
                kunden[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < mitarbeiter.length; i++) {
            try {
                mitarbeiter[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
