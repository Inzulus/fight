package player;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PlaylistManager {

    private Playlist aktuellePlaylist;
    private ArrayList<Playlist> plList = new ArrayList<>();
    private BufferedImage trackImage;


    //Kontruktor:
    public PlaylistManager(){
    }


    //Playlist einlesen und spreichern:
    public Playlist getPlaylist(String name){

        Playlist pl = new Playlist(name);
        Path file = Paths.get(name);

        try {
            BufferedReader reader = Files.newBufferedReader(file);
            String zeile = null;
            while ((zeile = reader.readLine()) != null) {

                //Tracks Zeile für Zeile einlesen und als Objekt in die Playlist speichern:
                try {
                    Mp3File mp3file = new Mp3File(zeile);
                    ID3v1 id3v1Tag = mp3file.getId3v1Tag();

                    if(mp3file.getId3v2Tag().getAlbumImage()!=null) {
                         trackImage = ImageIO.read(new ByteArrayInputStream(mp3file.getId3v2Tag().getAlbumImage()));
                    }
                    else{
                        File newFile = new File("files/cover.png");
                        trackImage = ImageIO.read(newFile);
                    }

                    Track newTrack = new Track(id3v1Tag.getTitle(), id3v1Tag.getArtist(), zeile,mp3file.getLengthInSeconds(),trackImage);
                    pl.addTrack(newTrack);

                } catch (InvalidDataException e) {
                    System.out.println("Invalid Mp3File");
                } catch (UnsupportedTagException e) {
                    System.out.println("Unsupported Tags");
                }
            }

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        plList.add(pl);
        return pl;
    }
}
