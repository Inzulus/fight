package player;

import java.awt.image.BufferedImage;

public class Track {

    private String name;
    private String artist;
    private String path;
    private BufferedImage image;
    private long length;


    //Kontruktor:
    public Track(String name, String artist, String path,long length, BufferedImage image){
        this.artist = artist;
        this.name = name;
        this.length = length;
        this.path = path;
        this.image = image;
    }


    //David Guetta:
    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getPath() {
        return path;
    }

    public BufferedImage getImage() {
        return image;
    }

    public long getLength(){
        return length;
    }


    //TOSTRING:
    @Override
    public String toString(){
          return name+" - "+artist+"   "+length/60+":"+length%60;
    }
}
