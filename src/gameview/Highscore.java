package gameview;

import player.Track;

public class Highscore {
    private int score;
    private String spielerName;
    private Track track;

    public Highscore(Track track){
        this.track = track;
        score = 0;
        spielerName = "";
    }

    public void setSpielerName(String spielerName){
        this.spielerName = spielerName;
    }

    public void addScore(int score){
        this.score += score;
    }
}
