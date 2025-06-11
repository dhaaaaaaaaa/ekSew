package dhaas;

import java.util.*;
import java.util.concurrent.*;

public class Ticketsystem {
    private int letztesTicket = 0;
    private int aktuellesTicket = 0;
    private final Object monitor = new Object();

    private final Map<Integer, String> anliegenMap = new ConcurrentHashMap<>();
    private final Map<Integer, CountDownLatch> ticketFertig = new ConcurrentHashMap<>();

    public int takeTicket() {
        synchronized (monitor) {
            int ticket = letztesTicket++;
            ticketFertig.put(ticket, new CountDownLatch(1));
            return ticket;
        }
    }

    public void warteAufTicket(int meineNummer) throws InterruptedException {
        synchronized (monitor) {
            while (meineNummer != aktuellesTicket) {
                monitor.wait();
            }
        }
    }

    public int getNextTicket() {
        synchronized (monitor) {
            int ticket = aktuellesTicket;
            aktuellesTicket++;
            monitor.notifyAll();
            return ticket;
        }
    }

    public void ticketBearbeitet(int ticketNummer, String anliegen) {
        anliegenMap.put(ticketNummer, anliegen);
        ticketFertig.get(ticketNummer).countDown();
    }

    public void warteBisAnliegenVorgetragen(int ticketNummer) throws InterruptedException {
        CountDownLatch latch = ticketFertig.get(ticketNummer);
        if (latch != null) {
            latch.await();
        }
    }

    public String getAnliegen(int ticketNummer) {
        return anliegenMap.get(ticketNummer);
    }
}
