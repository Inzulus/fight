package gameview;

import player.Track;

import java.awt.*;

public class Highscore implements Comparable<Highscore> {
    private int score;
    private String spielerName;
    private Track track;

    public Highscore(Track track,int score){
        this.track = track;
        this.score = score;
        spielerName = "";
    }

    public Highscore(Track track,String name, int score){
        this.track = track;
        this.score = score;
        this.spielerName = name;
    }

    public void setSpielerName(String spielerName){
        this.spielerName = spielerName;
    }

    public void addScore(int score){
        this.score += score;
    }

    @Override
    public int compareTo(Highscore o) {
        return (this.score - o.score);
    }

    public int getScore() {
        return score;
    }

    public String getSpielerName() {
        return spielerName;
    }

    public Track getTrack() {
        return track;
    }
}
