package player;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.*;
import de.hsrm.mi.eibo.simpleplayer.MinimHelper;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MP3Player {

    private SimpleMinim minim = new SimpleMinim();
    private SimpleAudioPlayer audioPlayer;

    public BeatDetect beatDetect;
    private Minim bigMinim;
    public AudioPlayer bigAudioPlayer;
    float kickSize, snareSize, hatSize;
    public BeatListener bl;

    private int currentTrackNumber = 0;
    private Playlist currentPlaylist;
    private List listener = new ArrayList();

    private Thread playThread;
    private Thread timeThread;
    private TimeProperty currentTime = new TimeProperty();
    private SimpleBooleanProperty isPlayingProperty = new SimpleBooleanProperty();

    private boolean isPlaying = false;
    private boolean shuffle;



    //Konstruktor:
    public MP3Player(String filename){
        audioPlayer = minim.loadMP3File(filename);

        MinimHelper minimHelper = new MinimHelper();
        bigMinim = new Minim(minimHelper);
        bigAudioPlayer = bigMinim.loadFile(filename,1024);

        beatDetect = new BeatDetect(bigAudioPlayer.bufferSize(), bigAudioPlayer.sampleRate());
        beatDetect.setSensitivity(300);
        kickSize = snareSize = hatSize = 16;
        bl = new BeatListener(beatDetect, bigAudioPlayer);
    }


    public MP3Player(){
    }

    public void playWithBeatThread(){
        startTimer();
        playThread = new Thread(){
            public void run(){
                bigAudioPlayer.play();
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


    //Playlist laden:
    public void loadPlaylist(String filename) {
        PlaylistManager plManager = new PlaylistManager();
        currentPlaylist = plManager.getPlaylist(filename);
        audioPlayer = minim.loadMP3File(currentPlaylist.getTrack(currentTrackNumber).getPath());

        info();
    }


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

    public void playThread(){
        startTimer();
        //fireInfoEvent();
        playThread = new Thread(){
            public void run(){
                audioPlayer.play();
                if(isPlaying)
                    onCompletion();
            }
        };
        playThread.setDaemon(true);
        playThread.start();
        isPlaying = true;
    }


    //Play-Methoden:
    public void play(){
        //info();
        isPlaying = true;
        isPlayingProperty.set(true);
        playThread();
    }


    public void play(Track track,int currentTrackNumber){
        isPlaying = true;

        isPlayingProperty.set(true);
        this.currentTrackNumber = currentTrackNumber;
        minim.stop();
        audioPlayer = minim.loadMP3File(track.getPath());
        playThread();
    }


    //Überspringen:
    public void next() {
        minim.stop();
        if(shuffle){
            currentTrackNumber = (int) (Math.random()*currentPlaylist.getTrackCount());
        }
        else if(currentTrackNumber < currentPlaylist.getTrackCount() - 1){
            currentTrackNumber++;
        }
        else{
            currentTrackNumber=0;

        }
        audioPlayer = minim.loadMP3File(currentPlaylist.getTrack(currentTrackNumber).getPath());
        info();
        fireInfoEvent();
        if(isPlaying)
            playThread();
    }

    public void prev(){
        minim.stop();
        if(shuffle){
            currentTrackNumber = (int) (Math.random()*currentPlaylist.getTrackCount());
        }
        else if(currentTrackNumber==0) {
            currentTrackNumber = currentPlaylist.getTrackCount()-1;
        }
        else{
            currentTrackNumber--;
        }
        audioPlayer = minim.loadMP3File(currentPlaylist.getTrack(currentTrackNumber).getPath());
        info();
        if(isPlaying)
            playThread();
    }

    public void skip(int seconds){ audioPlayer.skip(seconds*1000); }


    //normaler Loop:
    public void onCompletion(){
        if(shuffle){
            currentTrackNumber = (int) (Math.random()*currentPlaylist.getTrackCount());
        }
        else if(currentTrackNumber < currentPlaylist.getTrackCount() - 1){
            currentTrackNumber++;
        }
        else{
            currentTrackNumber=0;

        }
        audioPlayer = minim.loadMP3File(currentPlaylist.getTrack(currentTrackNumber).getPath());
        info();
        fireInfoEvent();
        playThread();
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


    public void stop(){
        isPlayingProperty.set(false);
        timeThread.interrupt();
        minim.stop();
    }

    //Artist und Sontitel INFO:
    public void info(){
        System.out.print(currentPlaylist.getTrack(currentTrackNumber).getArtist()+" - ");
        System.out.println(currentPlaylist.getTrack(currentTrackNumber).getName());
    }


    //DAVID SUETTA
    public void setShuffle(boolean shuffle) { this.shuffle = shuffle; }

    public void setIsPlayingProperty(boolean playing){
        isPlayingProperty.set(playing);
    }

    public void setCurrentTrackNumber(int currentTrackNumber) {
        this.currentTrackNumber = currentTrackNumber;
    }

    //DAVID GUETTA

    public int getCurrentTrackNumber(){
        return currentTrackNumber;
    }
    public SimpleBooleanProperty getIsPlayingProperty(){
        return isPlayingProperty;
    }
    public TimeProperty getCurrentTimeProperty(){ return currentTime; }

    public Playlist getCurrentPlaylist() { return currentPlaylist; }

    public Track getCurrentTrack(){ return currentPlaylist.getTrack(currentTrackNumber); }

    public boolean isPlaying(){ return isPlaying; }

}
