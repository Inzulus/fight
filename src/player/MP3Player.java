package player;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import de.hsrm.mi.eibo.simpleplayer.MinimHelper;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.Iterator;
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
    private boolean isPaused = false;
    private Track currentTrack;
    private SimpleBooleanProperty isFinished = new SimpleBooleanProperty(false);


    //Konstruktor:
    public MP3Player(String filename){

        MinimHelper minimHelper = new MinimHelper();
        minim = new Minim(minimHelper);
        loadTrack(filename);

    }


    //Erzeugen von Tracks:
    public void loadTrack(String filename) {
             currentTrack = new Track(filename);
            audioPlayer = minim.loadFile(currentTrack.getPath(),1024);

            beatDetect = new BeatDetect(audioPlayer.bufferSize(), audioPlayer.sampleRate());
            beatDetect.setSensitivity(300);
            kickSize = snareSize = hatSize = 16;
            bl = new BeatListener(beatDetect, audioPlayer);
            fireInfoEvent();
    }


    //Haupt-Play Methode:
    public void playWithBeatThread(){
        startTimer();
        isFinished.set(false);

        playThread = new Thread(){
            public void run(){
                audioPlayer.play();
                while (audioPlayer.isPlaying() || (!audioPlayer.isPlaying() && isPaused)){
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                    }
                }
                isFinished.set(true);
                isPlaying = false;
            }
        };
        playThread.setDaemon(true);
        playThread.start();
        isPlaying = true;
        isPlayingProperty.set(true);
    }


    //feuert das Infoevent um das Interface vom Songwechsel zu informieren:
    private synchronized void fireInfoEvent(){
        InfoEvent ie = new InfoEvent(this,getCurrentTrack());
        Iterator listener = this.listener.iterator();
        while(listener.hasNext()){
            ((InfoListener)(listener.next())).infoReceived(ie);
        }
    }
    public synchronized void addInfoListener(InfoListener il){ listener.add(il); }
    public synchronized void removeInfoListener(InfoListener il){ listener.remove(il); }


    //Start_Methoden zum abspielen eines Songs:
    public void startTimer(){
        currentTime.setTime(0);
        if(timeThread!=null)
            timeThread.interrupt();
        timeThread = new Thread() {
                public void run() {
                while(!isInterrupted()){
                    currentTime.setTime(audioPlayer.position()/1000);
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        this.interrupt();
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
        isPaused = true;
        isPlayingProperty.set(false);
        audioPlayer.pause();
    }

    public void resume(){
        isPlaying = true;
        isPaused = false;
        isPlayingProperty.set(true);
        audioPlayer.play();
    }


    public void stop(){
        isPlayingProperty.set(false);
        isPlaying = false;
        minim.stop();
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
    public boolean isPaused(){
        return  isPaused;
    }
    public SimpleBooleanProperty getIsFinished(){
        return isFinished;
    }
}
