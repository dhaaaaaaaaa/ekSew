package dhaas;

import java.util.concurrent.CountDownLatch;

public class TicketInfo {
    private final int nummer;
    private final String name;
    private final String anliegen;
    private final CountDownLatch bereitLatch = new CountDownLatch(1);
    private final CountDownLatch fertigLatch = new CountDownLatch(1);
    private String vorgetragenesAnliegen;

    public TicketInfo(int nummer, String name, String anliegen) {
        this.nummer = nummer;
        this.name = name;
        this.anliegen = anliegen;
    }

    public int getNummer() {
        return nummer;
    }

    public String getName() {
        return name;
    }

    public String getAnliegen() {
        return anliegen;
    }

    public void wartenBisDran() throws InterruptedException {
        bereitLatch.await();
    }

    public void aufgerufenWerden() {
        bereitLatch.countDown();
    }

    public void anliegenVortragen(String text) {
        this.vorgetragenesAnliegen = text;
        fertigLatch.countDown();
    }

    public void wartenBisAnliegenVorgetragen() throws InterruptedException {
        fertigLatch.await();
    }

    public String getVorgetragenesAnliegen() {
        return vorgetragenesAnliegen;
    }
}
