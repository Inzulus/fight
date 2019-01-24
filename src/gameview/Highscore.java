package gameview;

import player.Track;

import java.awt.*;

public class Highscore {
    private int score;
    private String spielerName;
    private Track track;

    public Highscore(Track track,int score){
        this.track = track;
        this.score = score;
        spielerName = "";
    }

    public  Highscore(Track track,String name, int score){
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
}
