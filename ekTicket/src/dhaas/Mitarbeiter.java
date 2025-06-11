package dhaas;

public class Mitarbeiter implements Runnable {
    private final String name;
    private final Ticketsystem system;
    private final Ausgabe ausgabe;
    private final int maxTickets;

    public Mitarbeiter(String name, Ticketsystem system, Ausgabe ausgabe, int maxTickets) {
        this.name = name;
        this.system = system;
        this.ausgabe = ausgabe;
        this.maxTickets = maxTickets;
    }

    @Override
    public void run() {
        ausgabe.sagen("Schalter " + name + " ist bereit");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            for (int i = 0; i < maxTickets; i++) {
                int ticket = system.getNextTicket();
                ausgabe.sagen("Schalter " + name + " übernimmt #" + ticket);
                system.warteBisAnliegenVorgetragen(ticket); // blockiert, bis Kunde fertig ist
                String anliegen = system.getAnliegen(ticket);
                ausgabe.sagen("Schalter " + name + ": das Anliegen bei #" + ticket + " war '" + anliegen + "'");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            ausgabe.sagen("Schalter " + name + " wurde unterbrochen.");
        }

        ausgabe.sagen("Schalter " + name + " ist fertig.");
    }
}
