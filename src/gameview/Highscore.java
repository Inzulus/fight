package gameview;

import player.Track;

public class Highscore implements Comparable<Highscore>{
    private int score;
    private String spielerName;
    private Track track;


    //Kontruktor:
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


    @Override
    public int compareTo(Highscore o) {
        return (o.score - this.score);
    }


    //Setter:
    public void setSpielerName(String spielerName){
        this.spielerName = spielerName;
    }


    //Getter:
    public int getScore() {
        return score;
    }

    public String getSpielerName() {
        return spielerName;
    }

    public Track getTrack() {
        return track;
    }

    public String toString(){
        return score+" "+spielerName+" "+track.getName();
    }
}
