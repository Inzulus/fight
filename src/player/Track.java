package player;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

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

    public Track(String filename) {
        try {
            Mp3File mp3file = new Mp3File(filename);
            ID3v1 id3v1Tag = mp3file.getId3v1Tag();
            name = id3v1Tag.getTitle();
            artist = id3v1Tag.getArtist();
            path = filename;
            length = mp3file.getLengthInSeconds();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }



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
