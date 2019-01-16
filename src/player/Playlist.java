package player;

import java.util.ArrayList;

public class Playlist {
    private ArrayList<Track> songs = new ArrayList<Track>();
    private String name;


    //Kontruktor:
    public Playlist(String name){
        this.name = name;
    }


    //Track hinzufügen:
    public void addTrack(Track track){
        songs.add(track);
    }


    //GETTER:
    public Track getTrack(int n){
        return songs.get(n);
    }

    public int getTrackCount(){
        return songs.size();
    }

    public String getName(){
        return name;
    }


}
