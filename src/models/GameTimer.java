package models;

import java.time.Duration;
import java.time.Instant;

/**
 * Lihtne mängu ajamõõtja, mis võimaldab:
 * Aja käivitamist
 * Peatamist
 * Nullist uuesti alustamist
 * Aega mõõdetakse süsteemi kella alusel
 */
public class GameTimer {
    private Instant startTime;
    private boolean running;
    private Duration duration = Duration.ZERO;

    /**
     * Käivitab taimeri nullist. Kui juba töötas, siis alustab uuesti algusest
     */
    public void start() {
        startTime = Instant.now(); //Alustame aja mõõtmist praegusest hetkest
        running = true;
        duration = Duration.ZERO; //Taaskäivitis nullist
    }

    /**
     * Peatab taimeri, aeg peatub
     */
    public void stop() {
        if(running && startTime != null) {
            duration = Duration.between(startTime, Instant.now());
        }
        running = false;
    }

    /**
     * kas taimer töötab
     * @return true jah, false ei
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Tagastab, kui palju aega on mõõdunud sekundites
     * @return aeg sekundites
     */
    public int getElapsedSeconds() {
        if(startTime == null) {
            return 0;
        }
        if(running) {
            return (int) Duration.between(startTime, Instant.now()).getSeconds();
        } else {
            return (int) duration.getSeconds();
        }
    }

    /**
     * Vormindab aja sekunditest kujule MM:SS
     * @return vormindatud aeg
     */
    public String formatGameTime() {
        int totalSeconds = getElapsedSeconds();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
