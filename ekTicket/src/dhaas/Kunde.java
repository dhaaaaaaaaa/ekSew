package dhaas;

public class Kunde implements Runnable {
    private final String name;
    private final Ticketsystem system;
    private final String anliegen;
    private final Ausgabe ausgabe;

    public Kunde(String name, Ticketsystem system, String anliegen, Ausgabe ausgabe) {
        this.name = name;
        this.system = system;
        this.anliegen = anliegen;
        this.ausgabe = ausgabe;
    }

    @Override
    public void run() {
        try {
            int ticketNummer = system.takeTicket();
            ausgabe.sagen(name + " hat Ticket #" + ticketNummer + " und wartet.");

            system.warteAufTicket(ticketNummer);

            ausgabe.sagen(name + " ist dran mit Anliegen: " + anliegen);
            Thread.sleep(300);
            ausgabe.sagen(name + " hat das Anliegen vollständig vorgetragen");
            system.ticketBearbeitet(ticketNummer, anliegen);
            ausgabe.sagen(name + " geht wieder.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            ausgabe.sagen(name + " wurde unterbrochen.");
        }
    }
}
