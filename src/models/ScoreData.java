package models;

import java.time.LocalDateTime;

public class ScoreData implements Comparable<ScoreData> {
    private String name;
    private int time; //Mängu aeg sekundites
    private int click;
    private int board; //Mängulaua suurus
    private LocalDateTime playedTime;

    public ScoreData(String name, int time, int click, int board, LocalDateTime playedTime) {
        this.name = name;
        this.time = time;
        this.click = click;
        this.board = board;
        this.playedTime = playedTime;
    }

    //GETTERS

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getClicks() {
        return click;
    }

    public int getBoard() {
        return board;
    }

    public LocalDateTime getPlayedTime() {
        return playedTime;
    }

    @Override
    public String toString() {
        return "ScoreData{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", click=" + click +
                ", board=" + board +
                ", playedTime=" + playedTime +
                '}';
    }

    /**
     * Sorteerib time, clicks, ja playestime järgi kasvavalt
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(ScoreData o) {
        // Kasutamine mujal: Collections.sort(MASSIIVINIMI)
        int cmp = Integer.compare(this.time, o.time);
        if (cmp != 0) return cmp;
        cmp = Integer.compare(this.click, o.click);
        if (cmp != 0) return cmp;

        return this.playedTime.compareTo(o.playedTime);
    }

    /**
     * Vormindab etteantud aja sekundid kujule MM:SS
     *
     * @param seconds sekundid kujul 98
     * @return vormindatud aeg
     */
    public String formatGameTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}
