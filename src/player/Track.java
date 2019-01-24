package player;

public class Track {

    private String name;
    private String artist;
    private String path;
    private long length;


    //Kontruktor:
    public Track(String name, String artist, String path,long length){
        this.artist = artist;
        this.name = name;
        this.length = length;
        this.path = path;
    }

    public Track() {

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

    public long getLength(){
        return length;
    }


    //TOSTRING:
    @Override
    public String toString(){
          return name+" - "+artist+"   "+length/60+":"+length%60;
    }
}
