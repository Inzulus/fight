package player;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import de.hsrm.mi.eibo.simpleplayer.MinimHelper;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MP3Player {

    public BeatDetect beatDetect;
    private Minim minim;
    public AudioPlayer audioPlayer;
    float kickSize, snareSize, hatSize;
    public BeatListener bl;

    private List listener = new ArrayList();

    private Thread playThread;
    private Thread timeThread;
    private TimeProperty currentTime = new TimeProperty();
    private SimpleBooleanProperty isPlayingProperty = new SimpleBooleanProperty();

    private boolean isPlaying = false;
    private Track currentTrack;


    //Konstruktor:
    public MP3Player(String filename){
        loadTrack(filename);

        MinimHelper minimHelper = new MinimHelper();
        minim = new Minim(minimHelper);
        audioPlayer = minim.loadFile(filename,1024);

        beatDetect = new BeatDetect(audioPlayer.bufferSize(), audioPlayer.sampleRate());
        beatDetect.setSensitivity(300);
        kickSize = snareSize = hatSize = 16;
        bl = new BeatListener(beatDetect, audioPlayer);
    }


    public MP3Player(){
    }

    //Erzeugen von Tracks:
    public void loadTrack(String filename) {

        try{
            Mp3File mp3file = new Mp3File(filename);
            ID3v1 id3v1Tag = mp3file.getId3v1Tag();

            currentTrack = new Track(id3v1Tag.getTitle(), id3v1Tag.getArtist(), filename, mp3file.getLengthInSeconds());

        } catch (InvalidDataException e) {
            System.out.println("Invalid Mp3File");
        } catch (UnsupportedTagException e) {
            System.out.println("Unsupported Tags");
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }


    //Haupt-Play Methode:
    public void playWithBeatThread(){
        startTimer();
        playThread = new Thread(){
            public void run(){
                audioPlayer.play();
            }
        };
        playThread.setDaemon(true);
        playThread.start();
        isPlaying = true;
        isPlayingProperty.set(true);
    }


    //feuert das Infoevent um das Interface vom Songwechsel zu informieren:
    /*private synchronized void fireInfoEvent(){
        InfoEvent ie = new InfoEvent(this,getCurrentTrack());
        Iterator listener = this.listener.iterator();
        while(listener.hasNext()){
            ((InfoListener)(listener.next())).infoReceived(ie);
        }
    }
    public synchronized void addInfoListener(InfoListener il){ listener.add(il); }
    public synchronized void removeInfoListener(InfoListener il){ listener.remove(il); }*/


    //Start_Methoden zum abspielen eines Songs:
    public void startTimer(){
        currentTime.setTime(0);
        if(timeThread!=null)
            timeThread.interrupt();
        timeThread = new Thread() {
                public void run() {
                while(!isInterrupted()){
                    //System.out.println(audioPlayer.position());
                    currentTime.setTime(audioPlayer.position()/1000);
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        this.interrupt();
                        //e.printStackTrace();
                    }
                }
            }
        };
        timeThread.setDaemon(true);
        timeThread.start();
    }



    //Lautstärke:
    public void volume(float value){
        if(audioPlayer.isPlaying()){
            if(value>=0 && value <=2){
                float v = 20*(float)Math.log10(value);
                audioPlayer.setGain(v);
            }
        }
    }

    public void mute(){
        if(!audioPlayer.isMuted())
            audioPlayer.mute();
        else{
            audioPlayer.unmute();
        }
    }


    //Pause/Stop:
    public void pause(){
        isPlaying = false;
        isPlayingProperty.set(false);
        audioPlayer.pause();
    }

    public void resume(){
        isPlaying = true;
        isPlayingProperty.set(true);
        audioPlayer.play();
    }


    public void stop(){
        isPlayingProperty.set(false);
        timeThread.interrupt();
        minim.stop();
    }


    //Artist und Sontitel INFO:
    public void info(){
        //TODO
    }


    //DAVID SUETTA
    public void setIsPlayingProperty(boolean playing){
        isPlayingProperty.set(playing);
    }


    //DAVID GUETTA
    public SimpleBooleanProperty getIsPlayingProperty(){
        return isPlayingProperty;
    }
    public TimeProperty getCurrentTimeProperty(){ return currentTime; }
    public Track getCurrentTrack(){ return currentTrack; }
    public boolean isPlaying(){ return isPlaying; }

}
